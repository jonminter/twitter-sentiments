package com.jonminter.twitopin.datapipeline.nlp;

import com.jonminter.twitopin.datapipeline.models.Sentiment;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.ejml.simple.SimpleMatrix;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class StanfordNlpSentimentHelper implements SentimentHelper {
    private static final Map<Integer, Sentiment> sentimentScoreMap = new HashMap<>();
    static {
        sentimentScoreMap.put(0, Sentiment.NEGATIVE);
        sentimentScoreMap.put(1, Sentiment.NEGATIVE);
        sentimentScoreMap.put(2, Sentiment.NEUTRAL);
        sentimentScoreMap.put(3, Sentiment.POSITIVE);
        sentimentScoreMap.put(4, Sentiment.POSITIVE);
    }
    private StanfordCoreNLP pipeline;
    public StanfordNlpSentimentHelper() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    @Override
    public Sentiment getSentimentFromText(String text) {
        int mainSentiment = 0;
        if (text != null && text.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(text);
            for (CoreMap sentence : annotation
                    .get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence
                        .get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                SimpleMatrix sentiment_new = RNNCoreAnnotations.getPredictions(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = sentiment;
                    longest = partText.length();
                }
            }
        }
        return sentimentScoreMap.get(mainSentiment);
    }
}
