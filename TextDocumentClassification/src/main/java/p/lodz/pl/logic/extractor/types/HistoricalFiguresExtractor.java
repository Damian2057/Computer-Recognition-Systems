package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.CommonWords;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static p.lodz.pl.constants.Const.CURRENCY_DICTIONARY;
import static p.lodz.pl.constants.Const.HISTORICAL_FIGURES_DICTIONARY;

public class HistoricalFiguresExtractor implements SpecificExtractor {
    @Override
    public Feature<?> extract(Article article) {
        Map<String, Integer> map = new LinkedHashMap<>();
        List<String> words = CommonWords.applyDictionary(article, HISTORICAL_FIGURES_DICTIONARY);
        for (String word : words) {
            map.merge(word, 1, Integer::sum);
        }
        Optional<String> commonPerson = map.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);

        return new Feature<String>(Type.HISTORICAL_FIGURES, commonPerson.orElse("undefined"));
    }
}
