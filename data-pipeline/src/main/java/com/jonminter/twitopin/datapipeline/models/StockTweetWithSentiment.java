package com.jonminter.twitopin.datapipeline.models;

import org.apache.flink.api.java.tuple.Tuple4;

import java.util.StringJoiner;

/**
 * f0 - stock symbol
 * f1 - tweet text
 * f2 - sentiment score
 * f3 - count
 */
public class StockTweetWithSentiment extends Tuple4<String, String, Sentiment, Integer> {
    public static final int FIELD_STOCK_SYMBOL = 0;
    public static final int FIELD_TWEET_TEXT = 1;
    public static final int FIELD_SENTIMENT = 2;
    public static final int FIELD_COUNT = 3;
    public StockTweetWithSentiment() {
        super(null, null, null, 1);
    }

    public StockTweetWithSentiment(StockTweet tweet, Sentiment sentiment) {
        super(tweet.getStockSymbol(), tweet.getText(), sentiment, 1);
    }

    public String getStockSymbol() {
        return f0;
    }

    public String getText() {
        return f1;
    }

    public Sentiment getSentiment() {
        return f2;
    }

    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("stockSymbol=" + f0)
                .add("text=" + f1)
                .add("sentiment=" + f2)
                .toString();
    }
}
