package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.CommonWords;
import p.lodz.pl.logic.extractor.types.base.SpecificExtractor;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static p.lodz.pl.constants.Const.COMMON_WORDS_DICTIONARY;

public class FrequencyUniquenessWordsExtractor implements SpecificExtractor {
    @Override
    public Feature<Double> extract(Article article) {
        Map<String, Integer> map = new HashMap<>();
        List<String> words = CommonWords.removeWordsFromDictionary(article, COMMON_WORDS_DICTIONARY);
        for (String word : words) {
            map.merge(word, 1, Integer::sum);
        }

        double count = map.entrySet()
                .stream()
                .filter(entry -> entry.getValue() == 1)
                .count();
        count /= CommonWords.removeSpecialSymbols(article).size();

        return new Feature<>(Type.FREQUENCY_NUMBER_OF_UNIQUENESS_WORDS, count);
    }
}
