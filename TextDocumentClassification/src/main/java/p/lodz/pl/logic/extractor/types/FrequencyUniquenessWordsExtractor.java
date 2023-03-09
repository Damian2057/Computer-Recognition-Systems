package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.CommonWords;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FrequencyUniquenessWordsExtractor implements SpecificExtractor {
    @Override
    public Feature<?> extract(Article article) {
        Map<String, Integer> map = new HashMap<>();
        List<String> words = CommonWords.remove(article);
        for (String word : words) {
            map.merge(word, 1, Integer::sum);
        }

        double count = map.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 1)
                .count();
        count /= CommonWords.simpleRemove(article).size();

        return new Feature<Double>(Type.FREQUENCY_NUMBER_OF_UNIQUENESS_WORDS, count);
    }
}
