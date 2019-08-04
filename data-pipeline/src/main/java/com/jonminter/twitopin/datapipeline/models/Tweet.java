package com.jonminter.twitopin.datapipeline.models;

import java.util.StringJoiner;

public final class Tweet {
    private static final StringJoiner toStringJoiner = new StringJoiner(", ", Class.class.getSimpleName() + "[", "]");
    private static final long serialVersionUID = 1L;

    private final TweetUser user;
    private final String text;
    private final String lang;

    public Tweet() {
        this.user = null;
        this.text = null;
        this.lang = null;
    }

    public Tweet(String text, String lang, TweetUser user) {
        this.text = text;
        this.user = user;
        this.lang = lang;
    }

    public String getText() {
        return text;
    }

    public TweetUser getUser() {
        return user;
    }

    public String getLang() {
        return lang;
    }

    public String toString() {
        return toStringJoiner
                .add("text=" + text)
                .add("lang=" + lang)
                .toString();
    }
}
