package com.jonminter.twitopin.datapipeline.operators;

import com.jonminter.twitopin.datapipeline.models.Sentiment;
import com.jonminter.twitopin.datapipeline.models.StockTweet;
import com.jonminter.twitopin.datapipeline.models.StockTweetWithSentiment;
import com.jonminter.twitopin.datapipeline.nlp.NlpFactory;
import com.jonminter.twitopin.datapipeline.nlp.SentimentHelper;
import org.apache.flink.api.common.functions.MapFunction;

public class SentimentMapper implements MapFunction<StockTweet, StockTweetWithSentiment> {
    private transient SentimentHelper sentimentHelper;

    @Override
    public StockTweetWithSentiment map(StockTweet value) throws Exception {
        if (sentimentHelper == null) {
            sentimentHelper = NlpFactory.createSentimentHelper();
        }
        Sentiment sentiment = sentimentHelper.getSentimentFromText(value.getText());

        return new StockTweetWithSentiment(value, sentiment);
    }
}
