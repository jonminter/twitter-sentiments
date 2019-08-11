package com.jonminter.twitopin.datapipeline.operators;

import com.jonminter.twitopin.datapipeline.nlp.SentimentHelper;
import com.jonminter.twitopin.datapipeline.nlp.StanfordNlpSentimentHelper;
import com.jonminter.twitopin.datapipeline.models.Sentiment;
import com.jonminter.twitopin.datapipeline.models.Tweet;
import com.jonminter.twitopin.datapipeline.models.TweetWithSentiment;
import org.apache.flink.api.common.functions.MapFunction;

public class SentimentMapper implements MapFunction<Tweet, TweetWithSentiment> {
    private transient SentimentHelper sentimentHelper;

    @Override
    public TweetWithSentiment map(Tweet value) throws Exception {
        if (sentimentHelper == null) {
            sentimentHelper = new StanfordNlpSentimentHelper();
        }
        Sentiment sentiment = sentimentHelper.getSentimentFromText(value.getText());

        return new TweetWithSentiment(value, sentiment);
    }
}
