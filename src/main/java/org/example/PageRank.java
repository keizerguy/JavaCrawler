package org.example;

import java.util.*;
import java.util.regex.*;

public class PageRank {

    // Small Stop words
    private static final Set<String> SMALL_STOP_WORDS = Set.of(
            "the", "is", "in", "at", "of", "on", "and", "a", "to", "for", "with", "that", "this"
    );

    // Full stop words
    private static final Set<String> STOP_WORDS = Set.of(
            "i", "me", "my", "myself", "we", "our", "ours", "ourselves", "you", "your", "yours",
            "yourself", "yourselves", "he", "him", "his", "himself", "she", "her", "hers",
            "herself", "it", "its", "itself", "they", "them", "their", "theirs", "themselves",
            "what", "which", "who", "whom", "this", "that", "these", "those", "am", "is",
            "are", "was", "were", "be", "been", "being", "have", "has", "had", "having",
            "do", "does", "did", "doing", "a", "an", "the", "and", "but", "if", "or", "because",
            "as", "until", "while", "of", "at", "by", "for", "with", "about", "against",
            "between", "into", "through", "during", "before", "after", "above", "below",
            "to", "from", "up", "down", "in", "out", "on", "off", "over", "under", "again",
            "further", "then", "once", "here", "there", "when", "where", "why", "how",
            "all", "any", "both", "each", "few", "more", "most", "other", "some", "such",
            "no", "nor", "not", "only", "own", "same", "so", "than", "too", "very", "s",
            "t", "can", "will", "just", "should", "now", "de", "het", "een", "en",
            "van", "ik", "te", "dat", "die", "zij", "ze", "zal", "zullen", "zou", "zouden",
            "jij", "je", "niet", "voor", "met", "er", "als", "ben", "op", "mijn",
            "dit", "maar", "aan", "hij", "uit", "hoe", "wat", "zijn", "heb", "hun",
            "hier", "door", "nu", "bij", "naar", "dan", "nog", "waar", "mee", "geen",
            "meer", "dus", "al", "wel", "om", "ook", "zo", "toen", "hebben", "kan",
            "kon", "doet", "doen", "deze", "eenmaal", "tot", "onder", "sinds",
            "alles", "altijd", "dag", "heeft", "hebt", "hoewel", "kunnen", "moeten",
            "ons", "onze", "onszelf", "samen", "toch", "u", "uw", "veel", "volgens", "want",
            "weinig", "wil", "wilt", "b", "c", "d", "e", "f", "g", "h", "function", "window",
            "j", "k", "l", "m", "n", "o", "p", "q", "r", "v", "w", "x", "y", "z", "const",
            "foreach", "document", "var", "addeventlistener", "mw", "pages", "page", "edit",
            "www", "com", "org", "wikipedia"
    );

    // Window size for co-occurrence
    private static final int WINDOW_SIZE = 4;

    public static List<Map.Entry<String, Double>> extractKeywords(String text, int topN) {
        // Tokenize and normalize words
        List<String> words = Arrays.asList(tokenize(text));

        // Filter out stop words
        List<String> filteredWords = filterStopWords(words);

        // Build word graph
        Map<String, Set<String>> wordGraph = buildGraph(filteredWords);

        // Apply PageRank algorithm
        Map<String, Double> rankedWords = applyPageRank(wordGraph);

        // Sort words by PageRank score in descending order
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
            if (!STOP_WORDS.contains(word)) {
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

    // Apply PageRank algorithm
    private static Map<String, Double> applyPageRank(Map<String, Set<String>> graph) {
        final double d = 0.75; // Damping factor (0.85)
        final int maxIter = 150; // Maximum iterations (100)
        final double tol = 1e-7; // Convergence tolerance (1e-6)

        // Initialize PageRank scores
        Map<String, Double> scores = new HashMap<>();
        int totalWords = graph.size();
        for (String word : graph.keySet()) {
            scores.put(word, 1.0 / totalWords);
        }

        // PageRank iterations
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
