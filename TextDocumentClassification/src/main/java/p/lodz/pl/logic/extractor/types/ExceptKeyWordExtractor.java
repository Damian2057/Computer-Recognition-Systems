package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.FilterHelper;
import p.lodz.pl.logic.extractor.types.base.SpecificExtractor;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static p.lodz.pl.constants.Const.KEY_WORDS_DICTIONARY;

public class ExceptKeyWordExtractor implements SpecificExtractor {
    @Override
    public Feature<?> extract(Article article) {
        List<String> exceptKeyWords = FilterHelper.removeWordsFromDictionaryWithCommonWords(article, KEY_WORDS_DICTIONARY);
        Map<String, Integer> map = new LinkedHashMap<>();
        for (String word : exceptKeyWords) {
            map.merge(word, 1, Integer::sum);
        }
        Optional<String> word = map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);

        return new Feature<>(Type.EXCEPT_KEY_WORD, word.orElse("undefined"));
    }
}
