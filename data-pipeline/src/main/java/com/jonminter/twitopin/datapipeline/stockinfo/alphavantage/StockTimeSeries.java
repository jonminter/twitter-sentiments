package com.jonminter.twitopin.datapipeline.stockinfo.alphavantage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StockTimeSeries {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MetaData {
        @JsonProperty("3. Last Refreshed")
        public String lastRefreshed;

        @Override
        public String toString() {
            return new StringJoiner(",", MetaData.class.getSimpleName() + " [", "]")
                    .add("lastRefreshed=" + lastRefreshed)
                    .toString();
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StockDataPoint {
        @JsonProperty("1. open")
        public String open;
        @JsonProperty("2. high")
        public String high;
        @JsonProperty("3. low")
        public String low;
        @JsonProperty("4. close")
        public String close;
        @JsonProperty("5. volume")
        public String volume;

        @Override
        public String toString() {
            return new StringJoiner(",", StockDataPoint.class.getSimpleName() + " [", "]")
                    .add("open=" + open)
                    .add("high=" + high)
                    .add("low=" + low)
                    .add("close=" + close)
                    .add("volume=" + volume)
                    .toString();
        }
    }

    @JsonProperty("Meta Data")
    public MetaData meta;

    @JsonProperty("Time Series (1min)")
    public Map<String, StockDataPoint> timeSeries;

    @JsonProperty("Error Message")
    public String errorMessage;

    @Override
    public String toString() {
        return new StringJoiner(",", StockTimeSeries.class.getSimpleName() + " [", "]")
                .add("meta=" + meta)
                .add("timeSeries=" + timeSeries)
                .add("errorMessage=" + errorMessage)
                .toString();
    }
}
