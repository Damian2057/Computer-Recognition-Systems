package p.lodz.pl.logic.extractor.types.base;

import p.lodz.pl.constants.Const;
import p.lodz.pl.logic.FilterHelper;
import p.lodz.pl.model.Article;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class DictionaryExtractor {

    protected Optional<String> mostCommonItem(Article article, Const path) {
        Map<String, Integer> map = new LinkedHashMap<>();
        List<String> words = FilterHelper.keepWordsFromDictionary(article, path);
        for (String word : words) {
            map.merge(word, 1, Integer::sum);
        }

        return map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }
}
