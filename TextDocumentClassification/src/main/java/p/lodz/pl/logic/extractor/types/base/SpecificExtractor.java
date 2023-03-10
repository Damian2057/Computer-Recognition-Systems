package p.lodz.pl.logic.extractor.types.base;

import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

public interface SpecificExtractor {

    Feature<?> extract(Article article);
}
