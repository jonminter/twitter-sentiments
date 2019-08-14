/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.jonminter.twitopin.datapipeline;

import com.google.common.collect.Lists;
import com.jonminter.twitopin.datapipeline.models.*;
import com.jonminter.twitopin.datapipeline.operators.*;
import com.jonminter.twitopin.datapipeline.sources.SourceFactory;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.AsyncDataStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    private static final String OP_MAP_TWEET_JSON_TO_MODEL = "map_tweet_to_pojo";
    private static final String OP_FILTER_NON_EN_TWEETS = "filter_non_en_tweets";
    private static final String OP_CORRELATE_TO_STOCK = "map_correlated_to_stock";
    private static final String OP_DETERMINE_SENTIMENT = "map_determine_sentiment";
    private static final String OP_SUM_TWEETS = "sum_tweets_with_sentiment";
    private static final Time AGGREGATION_WINDOW = Time.seconds(5);

    private static final int STOCK_PRICE_TIMEOUT_MS = 1000;
    private static final int STOCK_PRICE_CONCURRENCY = 100;

    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromPropertiesFile("config/application.properties");

        StreamExecutionEnvironment sse = StreamExecutionEnvironment.getExecutionEnvironment();

        sse.getConfig().setGlobalJobParameters(params);
        sse.setParallelism(params.getInt("parallelism", 1));

        System.out.println(params.getProperties());

        List<StockToTrack> stocks = Lists.newArrayList(
                new StockToTrack("COF", Lists.newArrayList("cof", "capital one", "cap1", "capitalone")),
                new StockToTrack("WFC", Lists.newArrayList("wfc", "wells fargo", "wellsfargo")),
                new StockToTrack("BAC", Lists.newArrayList("BAC", "bank of america", "boa", "bankofamerica")));
        TwitterSource twitterSource = SourceFactory.createTwitterSource(params.getProperties(), stocks);
        DataStream<String> tweetStream = sse.addSource(twitterSource);

        /**
         * End goal
         * - Configuration of stock symbols to track, each symbol has an associated list of twitter phrases
         * - Tweet stream filtered by all stock phrases
         * - Map tween json to tweet model
         * - Filter by english only
         * - Add sentiment score
         * - Do a flat map where search tweets for phrases if matches a phrase then add stock symbol and emit for each match
         * - Create KeyedStream by stock symbol
         * - Key by sentiment
         * - Map to tuple of sentiment, count
         * - Time window
         * - Sum counts
         */
        DataStream<StockSentimentCount> stockSentimentStream = tweetStream
                .flatMap(new RawTweetMapper()) // Convert tweet json to text
                .uid(OP_MAP_TWEET_JSON_TO_MODEL)
                .filter(new EnglishTweetsOnlyFilter()) // Filter out non-english tweets
                .uid(OP_FILTER_NON_EN_TWEETS)
                .flatMap(new StockMentionTweetMapper(stocks))
                .uid(OP_CORRELATE_TO_STOCK)
                .map(new SentimentMapper()) // Run sentiment analysis and find which tweets are neg/neutral/pos
                .uid(OP_DETERMINE_SENTIMENT)
                .map(new MapFunction<StockTweetWithSentiment, StockSentimentCount>() {
                    @Override
                    public StockSentimentCount map(StockTweetWithSentiment value) throws Exception {
                        logger.info("Symbol: {}, Sentiment: {}", value.getStockSymbol(), value.getSentiment());
                        return new StockSentimentCount(value);
                    }
                })
                .keyBy(StockSentimentCount.FIELD_STOCK_SYMBOL, StockSentimentCount.FIELD_SENTIMENT)
                .timeWindow(AGGREGATION_WINDOW)
                .sum(StockSentimentCount.FIELD_COUNT)
                .uid(OP_SUM_TWEETS);

        DataStream<StockSentimentWithPrice> stockWithPriceStream = AsyncDataStream.unorderedWait(
                stockSentimentStream, new EnrichWithStockPrice(), STOCK_PRICE_TIMEOUT_MS, TimeUnit.MILLISECONDS,
                STOCK_PRICE_CONCURRENCY);

        stockWithPriceStream.print();

        sse.execute("Stock Twitter Sentiment");


    }
}
