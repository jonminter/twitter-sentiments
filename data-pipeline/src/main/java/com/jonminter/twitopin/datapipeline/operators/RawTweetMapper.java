package com.jonminter.twitopin.datapipeline.operators;

import com.jonminter.twitopin.datapipeline.models.Tweet;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.core.JsonParseException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.DeserializationFeature;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.util.Collector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class RawTweetMapper implements FlatMapFunction<String, Tweet> {
    private static final Logger logger = LoggerFactory.getLogger(Class.class);
    private static final long serialVersionUID = 1L;

    private transient ObjectMapper jsonParser;

    @Override
    public void flatMap(String value, Collector<Tweet> out) throws Exception {
        if (jsonParser == null) {
            jsonParser = new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }

        logger.trace("Raw tweet = {}", value);

        try {
            Tweet tweet = jsonParser.readValue(value, Tweet.class);
            out.collect(tweet);
        } catch (JsonParseException | JsonMappingException e) {
            logger.debug("Exception processing tweet = {}", e.getMessage());
//            logger.error("Exception occurred processing tweet! {}", e);
        }
    }
}
