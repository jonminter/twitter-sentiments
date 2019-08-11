package com.jonminter.twitopin.datapipeline.models;

import org.apache.flink.api.java.tuple.Tuple3;

import java.util.StringJoiner;

/**
 * f0 - Stock Symbol
 * f1 - Tweet Sentiment
 * f2 - Count
 */
public class StockSentimentCount extends Tuple3<String, Sentiment, Integer> {
    public final static int FIELD_STOCK_SYMBOL = 0;
    public final static int FIELD_SENTIMENT = 1;
    public final static int FIELD_COUNT = 2;

    public StockSentimentCount() {
        super(null, null, null);
    }

    public StockSentimentCount(StockTweetWithSentiment stockTweet) {
        super(stockTweet.getStockSymbol(), stockTweet.getSentiment(), 1);
    }

    public String getStockSymbol() {
        return f0;
    }

    public Sentiment getSentiment() {
        return f1;
    }

    public Integer getCount() {
        return f2;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("stockSymbol=" + f0)
                .add("sentiment=" + f1)
                .add("count=" + f2)
                .toString();
    }
}
