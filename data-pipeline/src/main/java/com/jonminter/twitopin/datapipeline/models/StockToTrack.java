package com.jonminter.twitopin.datapipeline.models;

import com.google.common.collect.ImmutableList;

import java.io.Serializable;
import java.util.List;

public final class StockToTrack implements Serializable {
    private static final long serialVersionUID = -5366684290976050177L;

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
