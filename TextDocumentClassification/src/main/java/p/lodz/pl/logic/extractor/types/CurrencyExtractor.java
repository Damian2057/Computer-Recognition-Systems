package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

public class CurrencyExtractor implements SpecificExtractor {
    @Override
    public Feature<?> extract(Article article) {
        return new Feature<>(Type.CURRENCY, "usa");
    }
}
