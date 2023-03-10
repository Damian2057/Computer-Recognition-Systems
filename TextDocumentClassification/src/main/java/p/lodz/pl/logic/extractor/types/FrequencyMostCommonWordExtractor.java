package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.logic.extractor.types.base.SpecificExtractor;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

public class FrequencyMostCommonWordExtractor implements SpecificExtractor {

    private final int numberOfOccurrences;

    public FrequencyMostCommonWordExtractor(int numberOfOccurrences) {
        this.numberOfOccurrences = numberOfOccurrences;
    }

    @Override
    public Feature<?> extract(Article article) {
        return null;
    }
}
