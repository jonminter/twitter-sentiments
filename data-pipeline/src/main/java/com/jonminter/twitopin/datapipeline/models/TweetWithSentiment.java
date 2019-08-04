package com.jonminter.twitopin.datapipeline.models;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

import java.util.StringJoiner;

/**
 * f0 - tweet text
 * f1 - sentiment score
 */
public class TweetWithSentiment extends Tuple2<String, Sentiment> {
    public TweetWithSentiment() {
        super(null, null);
    }

    public TweetWithSentiment(Tweet tweet, Sentiment sentiment) {
        super(tweet.getText(), sentiment);
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
