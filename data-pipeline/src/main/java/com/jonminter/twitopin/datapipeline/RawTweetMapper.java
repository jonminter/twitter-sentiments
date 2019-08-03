package com.jonminter.twitopin.datapipeline;

import com.jonminter.twitopin.datapipeline.models.Tweet;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;

public class RawTweetMapper implements MapFunction<String, Tweet> {
    private transient ObjectMapper jsonParser;

    @Override
    public Tweet map(String value) throws Exception {
        if (jsonParser == null) {
            jsonParser = new ObjectMapper();
        }

        Tweet tweet = jsonParser.readValue(value, Tweet.class);
        return tweet;
    }
}
