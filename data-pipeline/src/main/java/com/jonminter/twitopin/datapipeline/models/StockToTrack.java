package com.jonminter.twitopin.datapipeline.models;

import com.google.common.collect.ImmutableList;

import java.util.List;

public final class StockToTrack {
    private final String symbol;
    private final ImmutableList<String> twitterTerms;
    public StockToTrack(String symbol, List<String> twitterTerms) {
        this.symbol = symbol;
        this.twitterTerms = ImmutableList.copyOf(twitterTerms);
    }

    public String getSymbol() {
        return symbol;
    }

    public List<String> getTwitterTerms() {
        return this.twitterTerms;
    }
}
