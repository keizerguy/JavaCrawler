package org.example.ranking;

import java.util.Set;

public class StopWords {
    // Full stop words
    public static final Set<String> STOP_WORDS = Set.of(
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
}
