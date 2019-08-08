package com.jonminter.twitopin.datapipeline.models;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

import java.util.StringJoiner;

/**
 * f0 - tweet text
 * f1 - sentiment score
 */
public class TweetWithSentiment extends Tuple3<String, Sentiment, Integer> {
    public static final int FIELD_TWEET_TEXT = 0;
    public static final int FIELD_SENTIMENT = 1;
    public static final int FIELD_COUNT = 2;
    public TweetWithSentiment() {
        super(null, null, 1);
    }

    public TweetWithSentiment(Tweet tweet, Sentiment sentiment) {
        super(tweet.getText(), sentiment, 1);
    }

    public String getText() {
        return f0;
    }

    public Sentiment getSentiment() {
        return f1;
    }

    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("text=" + f0)
                .add("sentiment=" + f1)
                .toString();
    }
}
