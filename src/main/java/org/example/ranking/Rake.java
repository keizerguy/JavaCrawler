package org.example.ranking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Tried to also implement the Rake algorithm to rank the most important
 * keywords in a text string, but it's currently broken so not used.
 */
public class Rake {
    // Method to extract the top `n` keywords from the text
    public static List<String> extractRakeKeywords(String text, int topN) {
        List<String> phrases = splitIntoPhrases(text);
        Map<String, Double> wordScores = calculateWordScores(phrases);
        return rankPhrases(phrases, wordScores, topN);
    }

    // Method to split text into phrases, ignoring stop words
    private static List<String> splitIntoPhrases(String text) {
        List<String> phrases = new ArrayList<>();
        String[] words = text.split("\\s+");
        StringBuilder phrase = new StringBuilder();

        for (String word : words) {
            if (StopWords.STOP_WORDS.contains(word.toLowerCase())) {
                if (!phrase.isEmpty()) {
                    phrases.add(phrase.toString().trim());
                    phrase.setLength(0); // Reset phrase
                }
            } else {
                phrase.append(word).append(" ");
            }
        }
        if (!phrase.isEmpty()) {
            phrases.add(phrase.toString().trim());
        }
        return phrases;
    }

    // Method to calculate word scores based on frequency and degree
    private static Map<String, Double> calculateWordScores(List<String> phrases) {
        Map<String, Integer> wordFreq = new HashMap<>();
        Map<String, Integer> wordDegree = new HashMap<>();

        for (String phrase : phrases) {
            String[] words = phrase.split("\\s+");
            int degree = words.length - 1;
            for (String word : words) {
                wordFreq.put(word, wordFreq.getOrDefault(word, 0) + 1);
                wordDegree.put(word, wordDegree.getOrDefault(word, 0) + degree);
            }
        }

        Map<String, Double> wordScore = new HashMap<>();
        for (String word : wordFreq.keySet()) {
            wordScore.put(word, wordDegree.get(word) * 1.0 / wordFreq.get(word));
        }
        return wordScore;
    }

    // Method to rank phrases based on word scores and return the top `n`
    private static List<String> rankPhrases(List<String> phrases, Map<String, Double> wordScores, int topN) {
        Map<String, Double> phraseScores = new HashMap<>();

        for (String phrase : phrases) {
            double score = 0;
            for (String word : phrase.split("\\s+")) {
                score += wordScores.getOrDefault(word, 0.0);
            }
            phraseScores.put(phrase, score);
        }

        // Return the top `n` phrases based on their scores
        return phraseScores.entrySet().stream()
                .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())) // Sort in descending order of scores
                .limit(topN) // Limit to top `n` entries
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
