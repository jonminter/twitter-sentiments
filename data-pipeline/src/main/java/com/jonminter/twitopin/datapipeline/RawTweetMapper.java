package com.jonminter.twitopin.datapipeline;

import com.jonminter.twitopin.datapipeline.models.Tweet;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RawTweetMapper implements MapFunction<String, Tweet> {
    private static final Logger logger = LoggerFactory.getLogger(Class.class);
    private static final long serialVersionUID = 1L;

    private transient ObjectMapper jsonParser;

    @Override
    public Tweet map(String value) throws Exception {
        if (jsonParser == null) {
            jsonParser = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        logger.debug("Raw tweet = {}", value);

        Tweet tweet = jsonParser.readValue(value, Tweet.class);
        return tweet;
    }
}
