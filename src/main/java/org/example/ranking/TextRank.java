package org.example.ranking;

import java.util.*;

public class TextRank {
    // Window size for co-occurrence
    private static final int WINDOW_SIZE = 4;

    public static List<Map.Entry<String, Double>> extractKeywords(String text, int topN) {
        // Tokenize and normalize words
        List<String> words = Arrays.asList(tokenize(text));

        // Filter out stop words
        List<String> filteredWords = filterStopWords(words);

        // Build word graph
        Map<String, Set<String>> wordGraph = buildGraph(filteredWords);

        // Apply TextRank algorithm
        Map<String, Double> rankedWords = applyPageRank(wordGraph);

        // Sort words by TextRank score in descending order
        List<Map.Entry<String, Double>> sortedWords = new ArrayList<>(rankedWords.entrySet());
        sortedWords.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()));

        // Return the top N keywords
        return sortedWords.subList(0, Math.min(topN, sortedWords.size()));
    }

    // Tokenize and clean text
    private static String[] tokenize(String text) {
        String cleanedText = text.toLowerCase().replaceAll("[^a-z\\s]", " ");
        return cleanedText.split("\\s+");
    }

    // Filter stop words
    private static List<String> filterStopWords(List<String> words) {
        List<String> filtered = new ArrayList<>();
        for (String word : words) {
            if (!StopWords.STOP_WORDS.contains(word)) {
                filtered.add(word);
            }
        }
        return filtered;
    }

    // Build co-occurrence graph
    private static Map<String, Set<String>> buildGraph(List<String> words) {
        Map<String, Set<String>> graph = new HashMap<>();

        // Iterate through words with a sliding window
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            graph.putIfAbsent(word, new HashSet<>());

            // Add edges to words within the window
            for (int j = i + 1; j < Math.min(i + WINDOW_SIZE, words.size()); j++) {
                String neighbor = words.get(j);
                graph.get(word).add(neighbor);
                graph.putIfAbsent(neighbor, new HashSet<>());
                graph.get(neighbor).add(word); // Graph is bidirectional
            }
        }

        return graph;
    }

    // Apply TextRank algorithm
    private static Map<String, Double> applyPageRank(Map<String, Set<String>> graph) {
        final double d = 0.75; // Damping factor (0.85)
        final int maxIter = 150; // Maximum iterations (100)
        final double tol = 1e-7; // Convergence tolerance (1e-6)

        // Initialize TextRank scores
        Map<String, Double> scores = new HashMap<>();
        int totalWords = graph.size();
        for (String word : graph.keySet()) {
            scores.put(word, 1.0 / totalWords);
        }

        // TextRank iterations
        for (int iter = 0; iter < maxIter; iter++) {
            Map<String, Double> newScores = new HashMap<>();
            double maxChange = 0;

            // Calculate new scores
            for (String word : graph.keySet()) {
                double rank = (1 - d) / totalWords; // Teleportation term
                for (String neighbor : graph.get(word)) {
                    rank += d * (scores.get(neighbor) / graph.get(neighbor).size());
                }
                newScores.put(word, rank);
                maxChange = Math.max(maxChange, Math.abs(rank - scores.get(word)));
            }

            // Update scores
            scores = newScores;

            // Check convergence
            if (maxChange < tol) {
                break;
            }
        }
        return scores;
    }
}
