package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.extractor.types.base.DictionaryExtractor;
import p.lodz.pl.logic.extractor.types.base.SpecificExtractor;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import static p.lodz.pl.constants.Const.HISTORICAL_FIGURES_DICTIONARY;

public class HistoricalFiguresExtractor extends DictionaryExtractor implements SpecificExtractor {
    @Override
    public Feature<String> extract(Article article) {
        return new Feature<>(Type.HISTORICAL_FIGURES,
                mostCommonItem(article, HISTORICAL_FIGURES_DICTIONARY).orElse("undefined"));
    }
}
