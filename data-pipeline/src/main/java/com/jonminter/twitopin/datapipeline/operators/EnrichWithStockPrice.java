package com.jonminter.twitopin.datapipeline.operators;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.ListenableFuture;
import com.jonminter.twitopin.datapipeline.models.StockSentimentCount;
import com.jonminter.twitopin.datapipeline.models.StockSentimentWithPrice;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EnrichWithStockPrice implements AsyncFunction<StockSentimentCount, StockSentimentWithPrice> {
    @Override
    public ListenableFuture<StockSentimentWithPrice> apply(@Nullable StockSentimentCount input) throws Exception {
        return null;
    }
}
