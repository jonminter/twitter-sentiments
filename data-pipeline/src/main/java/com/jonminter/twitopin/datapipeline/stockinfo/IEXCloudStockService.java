package com.jonminter.twitopin.datapipeline.stockinfo;

import com.jonminter.twitopin.datapipeline.models.StockInfo;

import java.util.concurrent.CompletableFuture;

public class IEXCloudStockService implements StockInfoService {
    @Override
    public CompletableFuture<StockInfo> getStockInfo(String symbol) {
        return null;
    }
}
