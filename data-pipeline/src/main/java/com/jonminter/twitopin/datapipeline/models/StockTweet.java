package com.jonminter.twitopin.datapipeline.models;

import org.apache.flink.api.java.tuple.Tuple2;

import java.util.StringJoiner;

/**
 * f0 - Stock symbol
 * f1 - Tweet Text
 */
public class StockTweet extends Tuple2<String, String> {
    private static final long serialVersionUID = 4577420810966717330L;

    public static final int FIELD_STOCK_SYMBOL = 0;
    public static final int FIELD_TWEET_TEXT = 1;

    public StockTweet() {
        super(null, null);
    }

    public StockTweet(String stockSymbol, Tweet tweet) {
        super(stockSymbol, tweet.getText());
    }

    public String getStockSymbol() {
        return f0;
    }

    public String getText() {
        return f1;
    }

    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("stockSymbol=" + f0)
                .add("text=" + f1)
                .toString();
    }
}
