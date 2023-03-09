package p.lodz.pl.enums;

import p.lodz.pl.config.Config;
import p.lodz.pl.logic.extractor.types.*;

public enum Type {
    CURRENCY(new CurrencyExtractor()),
    NUMBER_OF_SENTENCES(new NumberOfSentencesExtractor()),
    HISTORICAL_FIGURES(new HistoricalFiguresExtractor()),
    PLACES(new PlacesExtractor()),
    COUNTRY(new CountryExtractor()),
    FREQUENCY_NUMBER_OF_UNIQUENESS_WORDS(new FrequencyUniquenessWordsExtractor()),
    NUMBER_OF_WORD_LENGTH(new WordLengthExtractor(Config.getProperties().getLongWordLength())),
    AVERAGE_WORD_LENGTH(new AverageWordLengthExtractor()),
    KEY_WORD(new KeyWordExtractor()),
    EXCEPT_KEY_WORD(new ExceptKeyWordExtractor()),
    FREQUENCY_OF_MOST_COMMON_WORD(new FrequencyMostCommonWordExtractor(Config.getProperties().getNumberOfOccurrences())),
    CAPITAL_WORD(new CapitalWordExtractor()),
    DOCUMENT_LENGTH(new DocumentLengthExtractor());

    private final SpecificExtractor extractor;

    Type(SpecificExtractor type) {
        this.extractor = type;
    }

    public SpecificExtractor getExtractor() {
        return extractor;
    }
}
