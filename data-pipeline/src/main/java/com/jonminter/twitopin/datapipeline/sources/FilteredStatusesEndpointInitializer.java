package com.jonminter.twitopin.datapipeline.sources;

import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.endpoint.StreamingEndpoint;
import org.apache.flink.streaming.connectors.twitter.TwitterSource;

import java.io.Serializable;
import java.util.List;

public class FilteredStatusesEndpointInitializer implements TwitterSource.EndpointInitializer, Serializable {
    private static final long serialVersionUID = 1788758413321977038L;

    private List<String> filterTerms;

    public FilteredStatusesEndpointInitializer(List<String> filterTerms) {
        this.filterTerms = filterTerms;
    }

    @Override
    public StreamingEndpoint createEndpoint() {
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
        hosebirdEndpoint.trackTerms(filterTerms);
        return hosebirdEndpoint;
    }
}
