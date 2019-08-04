package com.jonminter.twitopin.datapipeline;

import com.jonminter.twitopin.datapipeline.models.Sentiment;
import com.jonminter.twitopin.datapipeline.models.Tweet;
import com.jonminter.twitopin.datapipeline.models.TweetWithSentiment;
import org.apache.flink.api.common.functions.MapFunction;

public class SentimentMapper implements MapFunction<Tweet, TweetWithSentiment> {
    private SentimentHelper sentimentHelper;

    public SentimentMapper(SentimentHelper sentimentHelper) {
        this.sentimentHelper = sentimentHelper;
    }

    @Override
    public TweetWithSentiment map(Tweet value) throws Exception {
        Sentiment sentiment = sentimentHelper.getSentimentFromText(value.getText());

        return new TweetWithSentiment(value, sentiment);
    }
}
