package p.lodz.pl.enums;

import p.lodz.pl.logic.extractor.types.CurrencyExtractor;
import p.lodz.pl.logic.extractor.types.NumberOfSentencesExtractor;
import p.lodz.pl.logic.extractor.types.SpecificExtractor;

public enum Type {
    CURRENCY(new CurrencyExtractor()),
    NUMBER_OF_SENTENCES(new NumberOfSentencesExtractor());

    private final SpecificExtractor extractor;

    Type(SpecificExtractor type) {
        this.extractor = type;
    }

    public SpecificExtractor getExtractor() {
        return extractor;
    }
}
