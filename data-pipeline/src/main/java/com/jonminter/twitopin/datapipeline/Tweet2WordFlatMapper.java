package com.jonminter.twitopin.datapipeline;

import com.jonminter.twitopin.datapipeline.models.Tweet;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.flink.util.Collector;

import java.util.StringTokenizer;

public class Tweet2WordFlatMapper implements FlatMapFunction<Tweet, Tuple2<String, Integer>> {
    private static final long serialVersionUID = 1L;

    private final static String LANG_ENGLISH = "en";

    private class TweetUser {
        String lang;
    }

    @Override
    public void flatMap(Tweet tweet, Collector<Tuple2<String, Integer>> out) throws Exception {


        boolean isEnglish = tweet.getUser() != null && tweet.getLang() != null && LANG_ENGLISH.equals(tweet.getLang());
        boolean hasText = tweet.getText() != null;

        if (isEnglish && hasText) {
            String[] words = tweet.getText().split("\\s+");
            for (String word: words) {
                if (!words.equals("")) {
                    out.collect(new Tuple2<>(word, 1));
                }
            }
        }
    }
}
