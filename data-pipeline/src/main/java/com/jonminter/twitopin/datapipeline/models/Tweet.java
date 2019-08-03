package com.jonminter.twitopin.datapipeline.models;

public class Tweet {
    private final TweetUser user;
    private final String text;

    public Tweet(String text, TweetUser user) {
        this.text = text;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public TweetUser getUser() {
        return user;
    }
}
