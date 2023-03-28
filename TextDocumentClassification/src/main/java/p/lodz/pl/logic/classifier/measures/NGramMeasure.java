package p.lodz.pl.logic.classifier.measures;

import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;

import java.util.*;

public class NGramMeasure implements Measure {

    private static final Properties prop = Config.getProperties();

    @Override
    public double getDistanceBetweenTwoWords(String word1, String word2) {
        double similarity = 0;
        int N = Math.max(word1.length(), word2.length());
        int n1 = prop.getNgramLimitation()[0];
        int n2 = prop.getNgramLimitation()[1];
        String longest;
        String shorter;
        if (word1.length() > word2.length()) {
            longest = word1;
            shorter = word2;
        } else {
            longest = word2;
            shorter = word1;
        }

        for (int i = n1; i <= n2; i++) {
            List<String> ngrams1 = generateNgrams(longest, i);
            List<String> ngrams2 = generateNgrams(shorter, i);
            similarity += findNumberOfRepetitions(ngrams1, ngrams2);
        }

        similarity = 2 * similarity / ((N - n1 + 1) * (N - n1 + 2) - (N - n2 + 1) * (N - n2));

        return 1 - similarity;
    }

    private List<String> generateNgrams(String word, int n) {
        List<String> ngrams = new ArrayList<>();
        for (int i = 0; i < word.length() - n + 1; i++) {
            String ngram = word.substring(i, i + n);
            ngrams.add(ngram);
        }
        return ngrams;
    }

    private int findNumberOfRepetitions(List<String> ngrams1, List<String> ngrams2) {
        int count = 0;
        for (String element : ngrams1) {
            count += Collections.frequency(ngrams2, element);
        }

        return count;
    }
}
