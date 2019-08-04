package com.jonminter.twitopin.datapipeline.models;

import java.util.StringJoiner;

public final class TweetUser {
    private static final StringJoiner toStringJoiner = new StringJoiner(", ", Class.class.getSimpleName() + "[", "]");
    private static final long serialVersionUID = 1L;

    private final String lang;

    public TweetUser() {
        this.lang = null;
    }

    public TweetUser(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    @Override
    public String toString() {
        return toStringJoiner
                .add("lang=" + lang)
                .toString();
    }
}
