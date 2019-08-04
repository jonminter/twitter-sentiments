package com.jonminter.twitopin.datapipeline.models;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonCreator;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.annotation.JsonProperty;

import java.util.StringJoiner;

/**
 * f0 - text
 * f1 - lang
 */
public final class Tweet extends Tuple2<String, String> {
    private static final long serialVersionUID = 1L;

    public Tweet() {
        super(null, null);
    }

    @JsonCreator
    public Tweet(@JsonProperty("text") String text, @JsonProperty("lang") String lang) {
        super(text, lang);
    }

    public String getText() {
        return f0;
    }

    public String getLang() {
        return f1;
    }

    public String toString() {
        return new StringJoiner(", ", getClass().getSimpleName() + "[", "]")
                .add("text=" + f0)
                .add("lang=" + f1)
                .toString();
    }
}
