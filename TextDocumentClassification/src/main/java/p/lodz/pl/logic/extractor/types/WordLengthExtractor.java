package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

public class WordLengthExtractor implements SpecificExtractor {

    private final int wordLength;

    public WordLengthExtractor(int wordLength) {
        this.wordLength = wordLength;
    }

    @Override
    public Feature<?> extract(Article article) {
        return null;
    }
}
