package com.jonminter.twitopin.datapipeline.sources;

import com.jonminter.twitopin.datapipeline.models.StockToTrack;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class SourceFactory {
    public static TwitterSource createTwitterSource(Properties config, List<StockToTrack> stockToTrack) {
        List<String> filterTerms = stockToTrack.stream()
                .flatMap(stock -> stock.getTwitterTerms().stream())
                .collect(Collectors.toList());
        TwitterSource twitterSource = new TwitterSource(config);
        twitterSource.setCustomEndpointInitializer(new FilteredStatusesEndpointInitializer(filterTerms));
        return twitterSource;
    }
}