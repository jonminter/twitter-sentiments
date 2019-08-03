package com.jonminter.twitopin.datapipeline.models;

public class TweetUser {
    private final String lang;

    public TweetUser(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }
}
