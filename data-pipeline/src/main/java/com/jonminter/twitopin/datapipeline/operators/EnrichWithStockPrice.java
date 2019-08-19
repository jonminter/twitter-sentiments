package com.jonminter.twitopin.datapipeline.operators;

import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.ListenableFuture;
import com.jonminter.twitopin.datapipeline.models.StockInfo;
import com.jonminter.twitopin.datapipeline.models.StockSentimentCount;
import com.jonminter.twitopin.datapipeline.models.StockSentimentWithPrice;
import com.jonminter.twitopin.datapipeline.stockinfo.StockInfoFactory;
import com.jonminter.twitopin.datapipeline.stockinfo.StockInfoService;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.async.ResultFuture;
import org.apache.flink.streaming.api.functions.async.RichAsyncFunction;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class EnrichWithStockPrice extends RichAsyncFunction<StockSentimentCount, StockSentimentWithPrice> {
    private final static Logger logger = LoggerFactory.getLogger(EnrichWithStockPrice.class);
    private transient StockInfoService stockInfoService;
    private Properties properties;

    public EnrichWithStockPrice(Properties properties) {
        this.properties = properties;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        stockInfoService = StockInfoFactory.createStockInfoService(this.properties);
    }

    @Override
    public void asyncInvoke(StockSentimentCount input, ResultFuture<StockSentimentWithPrice> resultFuture) throws Exception {
        final Future<StockInfo> stockInfo = stockInfoService.getStockInfo(input.getStockSymbol());

        CompletableFuture.supplyAsync(new Supplier<StockInfo>() {

            @Override
            public StockInfo get() {
                try {
                    return stockInfo.get();
                } catch (InterruptedException | ExecutionException e) {
                    logger.error("Exception occurred retrieving stock price! {}\n{}", e.getMessage(), e.getStackTrace());
                    return null;
                }
            }
        }).thenAccept( (StockInfo stockInfoResult) -> {
            resultFuture.complete(Collections.singleton(new StockSentimentWithPrice(input, stockInfoResult.getCurrentPrice())));
        });


    }
}
