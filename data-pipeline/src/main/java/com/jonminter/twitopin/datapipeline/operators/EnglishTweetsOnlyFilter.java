package com.jonminter.twitopin.datapipeline.operators;

import com.jonminter.twitopin.datapipeline.models.Tweet;
import org.apache.flink.api.common.functions.FilterFunction;

public class EnglishTweetsOnlyFilter implements FilterFunction<Tweet> {
    private final static String LANG_ENGLISH = "en";

    @Override
    public boolean filter(Tweet tweet) throws Exception {
        return tweet.getLang() != null && LANG_ENGLISH.equals(tweet.getLang());
    }
}
