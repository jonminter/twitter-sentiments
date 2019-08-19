package com.jonminter.twitopin.datapipeline.stockinfo;

import com.jonminter.twitopin.datapipeline.models.StockInfo;
import com.jonminter.twitopin.datapipeline.stockinfo.alphavantage.StockTimeSeries;
import org.glassfish.jersey.logging.LoggingFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class AlphaVantageStockService implements StockInfoService {
    private static final Logger logger = LoggerFactory.getLogger(AlphaVantageStockService.class);
    private static final java.util.logging.Logger juLogger = java.util.logging.Logger.getLogger(AlphaVantageStockService.class.getCanonicalName());
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
        logger.info("Using base URL {} to get stock info from AlphaVantage", baseUrl);
        LoggingFeature requestLogger = new LoggingFeature(juLogger, Level.INFO, null, null);
        Client client = ClientBuilder.newBuilder()
                .register(requestLogger)
                .newClient();

        return client
                .target(baseUrl)
                .queryParam("function", FUNCTION_INTRADAY)
                .queryParam("interval", INTERVAL)
                .queryParam("outputsize", OUTPUT_SIZE)
                .queryParam("symbol", symbol)
                .queryParam("apikey", apiKey)
                .request(MediaType.APPLICATION_JSON)
                .rx()
                .get(StockTimeSeries.class)
                .toCompletableFuture()
                .thenApply(timeSeries -> {
                    logger.info("Result from AlphaVantag: {}", timeSeries);
                    Double price = 0.0;
                    if (timeSeries.timeSeries != null && timeSeries.meta != null) {
                        price = Double.valueOf(timeSeries.timeSeries.get(timeSeries.meta.lastRefreshed).close);
                    }
                    return new StockInfo(symbol, BigDecimal.valueOf(price));
                });
    }
}
