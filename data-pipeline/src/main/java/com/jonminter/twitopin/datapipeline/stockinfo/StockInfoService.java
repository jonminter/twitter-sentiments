package com.jonminter.twitopin.datapipeline.stockinfo;

import com.jonminter.twitopin.datapipeline.models.StockInfo;

import java.util.concurrent.CompletableFuture;

public interface StockInfoService {
    public CompletableFuture<StockInfo> getStockInfo(String symbol);
}
