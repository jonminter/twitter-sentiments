package com.jonminter.twitopin.datapipeline.models;

import org.apache.flink.api.java.tuple.Tuple4;

import java.math.BigDecimal;
import java.util.StringJoiner;

/**
 * f0 - Stock Symbol
 * f1 - Tweet Sentiment
 * f2 - Count
 * f3 - Price
 */
public class StockSentimentWithPrice extends Tuple4<String, Sentiment, Integer, BigDecimal> {
    public final static int FIELD_STOCK_SYMBOL = 0;
    public final static int FIELD_SENTIMENT = 1;
    public final static int FIELD_COUNT = 2;
    public final static int FIELD_PRICE = 3;

    public StockSentimentWithPrice() {
        super(null, null, null, null);
    }

    public StockSentimentWithPrice(StockSentimentCount stockTweet, BigDecimal price) {
        super(stockTweet.getStockSymbol(), stockTweet.getSentiment(), stockTweet.getCount(), price);
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

    public BigDecimal getPrice() {
        return f3;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("stockSymbol=" + f0)
                .add("sentiment=" + f1)
                .add("count=" + f2)
                .add("price=" + f3)
                .toString();
    }
}
