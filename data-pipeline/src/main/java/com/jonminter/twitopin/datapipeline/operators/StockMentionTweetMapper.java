package com.jonminter.twitopin.datapipeline.operators;

import com.jonminter.twitopin.datapipeline.models.StockToTrack;
import com.jonminter.twitopin.datapipeline.models.StockTweet;
import com.jonminter.twitopin.datapipeline.models.Tweet;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class StockMentionTweetMapper implements FlatMapFunction<Tweet, StockTweet> {
    private static final Logger logger = LoggerFactory.getLogger(StockMentionTweetMapper.class);

    List<StockToTrack> stocks;

    public StockMentionTweetMapper(List<StockToTrack> stocks) {
        this.stocks = stocks;
    }

    @Override
    public void flatMap(Tweet value, Collector<StockTweet> out) throws Exception {
        stocks.stream()
                .filter(stock -> stock.getTwitterTerms().stream().anyMatch(term -> containsTerm(value, term)))
                .forEach(stockMentioned -> out.collect(new StockTweet(stockMentioned.getSymbol(), value)));
    }

    private static boolean containsTerm(Tweet tweet, String searchTerm) {
        //TODO: Use Stanford NLP to tokenize/match?
        boolean contains = tweet.getText().toLowerCase().contains(searchTerm.toLowerCase());

        logger.debug("Text: '{}' contains? '{}' = {}", tweet.getText(), searchTerm, contains);

        return contains;
    }
}
