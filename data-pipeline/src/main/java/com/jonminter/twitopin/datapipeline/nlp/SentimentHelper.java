package com.jonminter.twitopin.datapipeline.nlp;

import com.jonminter.twitopin.datapipeline.models.Sentiment;

public interface SentimentHelper {
    public Sentiment getSentimentFromText(String text);
}
