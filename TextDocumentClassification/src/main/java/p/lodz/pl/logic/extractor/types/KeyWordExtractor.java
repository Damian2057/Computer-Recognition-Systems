package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.logic.extractor.types.base.DictionaryExtractor;
import p.lodz.pl.logic.extractor.types.base.SpecificExtractor;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

public class KeyWordExtractor extends DictionaryExtractor implements SpecificExtractor {

    @Override
    public Feature<?> extract(Article article) {
        return null;
    }
}
