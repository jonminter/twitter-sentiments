package com.jonminter.twitopin.datapipeline.stockinfo;

import com.jonminter.twitopin.datapipeline.models.StockInfo;
import com.jonminter.twitopin.datapipeline.stockinfo.alphavantage.StockTimeSeries;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

public class AlphaVantageStockService implements StockInfoService {
    private static final String FUNCTION_INTRADAY = "TIME_SERIES_INTRADAY";
    private static final String INTERVAL = "1min";
    private static final String OUTPUT_SIZE = "compact";
    private final String baseUrl;
    private final String apiKey;

    public AlphaVantageStockService(String baseUrl, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiKey = apiKey;
    }

    @Override
    public CompletableFuture<StockInfo> getStockInfo(String symbol) {
        Client client = ClientBuilder.newClient();
        return client
                .target(baseUrl)
                .queryParam("function", FUNCTION_INTRADAY)
                .queryParam("interval", INTERVAL)
                .queryParam("outputsize", OUTPUT_SIZE)
                .queryParam("symbol", symbol)
                .queryParam("apiKey", apiKey)
                .request(MediaType.APPLICATION_JSON)
                .rx()
                .get(StockTimeSeries.class)
                .toCompletableFuture()
                .thenApply(timeSeries -> {
                    Double price = Double.valueOf(timeSeries.timeSeries.get(timeSeries.meta.lastRefreshed).close);
                    return new StockInfo(symbol, BigDecimal.valueOf(price));
                });
    }
}
