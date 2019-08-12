package com.jonminter.twitopin.datapipeline.models;

import java.math.BigDecimal;
import java.util.StringJoiner;

public class StockInfo {
    private final String symbol;
    private final BigDecimal currentPrice;

    public StockInfo(String symbol, BigDecimal currentPrice) {
        this.symbol = symbol;
        this.currentPrice = currentPrice;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("stockSymbol=" + symbol)
                .add("price=" + currentPrice)
                .toString();

    }
}
