package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.extractor.FilterHelper;
import p.lodz.pl.logic.extractor.types.base.SpecificExtractor;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FrequencyMostCommonWordExtractor implements SpecificExtractor {

    private final int numberOfOccurrences;

    public FrequencyMostCommonWordExtractor(int numberOfOccurrences) {
        this.numberOfOccurrences = numberOfOccurrences;
    }

    @Override
    public Feature<Double> extract(Article article) {
        List<String> words = FilterHelper.removeSpecialSymbols(article);
        Map<String, Integer> map = new LinkedHashMap<>();
        for (String word : words) {
            map.merge(word, 1, Integer::sum);
        }
        double count = map.values().stream()
                .filter(value -> value > numberOfOccurrences)
                .count();
        count /= words.size();

        return new Feature<>(Type.FREQUENCY_OF_MOST_COMMON_WORD, count);
    }
}
