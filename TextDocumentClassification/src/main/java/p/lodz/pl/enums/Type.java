package p.lodz.pl.enums;

import p.lodz.pl.logic.extractor.types.*;

public enum Type {
    CURRENCY(new CurrencyExtractor()),
    NUMBER_OF_SENTENCES(new NumberOfSentencesExtractor()),
    HISTORICAL_FIGURES(new HistoricalFiguresExtractor()),
    PLACES(new PlacesExtractor()),
    COUNTRY(new CountryExtractor()),
    NUMBER_OF_UNIQUENESS_WORDS(new UniquenessWordsExtractor()),
    NUMBER_OF_WORD_LENGTH(new WordLengthExtractor(7)),
    AVERAGE_WORD_LENGTH(new AverageWordLengthExtractor()),
    KEY_WORD(new KeyWordExtractor()),
    EXCEPT_KEY_WORD(new ExceptKeyWordExtractor()),
    MOST_COMMON_WORD(new MostCommonWordExtractor()),
    CAPITAL_LETTER_WORD(new CapitalWordExtractor()),
    DOCUMENT_LENGTH(new DocumentLengthExtractor());

    private final SpecificExtractor extractor;

    Type(SpecificExtractor type) {
        this.extractor = type;
    }

    public SpecificExtractor getExtractor() {
        return extractor;
    }
}
