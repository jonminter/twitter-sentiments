package com.jonminter.twitopin.datapipeline.nlp;

public class NlpFactory {
    public static SentimentHelper createSentimentHelper() {
        return new StanfordNlpSentimentHelper();
    }
}
