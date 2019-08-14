package com.jonminter.twitopin.datapipeline.stockinfo.alphavantage;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.MapDeserializer;
import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;

import java.util.Map;

public class StockTimeSeries {
    public static class MetaData {
        @JsonProperty("Last Refreshed")
        public String lastRefreshed;
    }

    public static class StockDataPoint {
        public String open;
        public String high;
        public String low;
        public String close;
        public String volume;
    }

    @JsonProperty("Meta Data")
    public MetaData meta;

    @JsonProperty("Time Series (1min)")
    public Map<String, StockDataPoint> timeSeries;
}
