package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.extractor.types.base.DictionaryExtractor;
import p.lodz.pl.logic.extractor.types.base.SpecificExtractor;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import static p.lodz.pl.constants.Const.COUNTRY_DICTIONARY;

public class CountryExtractor extends DictionaryExtractor implements SpecificExtractor {
    @Override
    public Feature<?> extract(Article article) {
        return new Feature<>(Type.COUNTRY,
                mostCommonItem(article, COUNTRY_DICTIONARY).orElse("undefined"));
    }
}
