package com.jonminter.twitopin.datapipeline.stockinfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class StockInfoFactory {
    private final static Logger logger = LoggerFactory.getLogger(StockInfoFactory.class);
    public static StockInfoService createStockInfoService(Properties properties) {
        logger.info("Properties: {}", properties);
        return new AlphaVantageStockService(
                properties.getProperty("stock.alphaVantage.baseUrl"),
                properties.getProperty("stock.alphaVantage.apiKey")
        );
    }
}
