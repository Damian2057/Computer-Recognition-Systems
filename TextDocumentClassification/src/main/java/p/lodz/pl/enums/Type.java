package p.lodz.pl.enums;

import p.lodz.pl.config.Config;
import p.lodz.pl.logic.extractor.types.*;
import p.lodz.pl.logic.extractor.types.base.SpecificExtractor;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

public enum Type {
    CURRENCY(new CurrencyExtractor(), "currency based extraction"),
    NUMBER_OF_SENTENCES(new NumberOfSentencesExtractor(), "sentence number based extraction"),
    HISTORICAL_FIGURES(new HistoricalFiguresExtractor(), "historical figures based extraction"),
    PLACES(new PlacesExtractor(), "site based extraction"),
    COUNTRY(new CountryExtractor(), "country based extraction"),
    FREQUENCY_NUMBER_OF_UNIQUENESS_WORDS(new FrequencyUniquenessWordsExtractor(), "frequency of unique words based extraction"),
    NUMBER_OF_WORD_N_LENGTH(new WordLengthExtractor(Config.getProperties().getLongWordLength()), "number of sentences of N length based extraction"),
    AVERAGE_WORD_LENGTH(new AverageWordLengthExtractor(), "average sentence length based extraction"),
    KEY_WORD(new KeyWordExtractor(), "Keyword based extraction"),
    EXCEPT_KEY_WORD(new ExceptKeyWordExtractor(), "exclusion of dictionary words based extraction"),
    FREQUENCY_OF_MOST_COMMON_WORD(new FrequencyMostCommonWordExtractor(Config.getProperties().getNumberOfOccurrences()), "frequency repetitive words based extraction"),
    CAPITAL_WORD(new CapitalWordExtractor(), "number of words beginning with a capital letter based extraction"),
    DOCUMENT_LENGTH(new DocumentLengthExtractor(), "article length based extraction");

    private final SpecificExtractor extractor;
    private final String name;

    Type(SpecificExtractor type, String name) {
        this.extractor = type;
        this.name = name;
    }

    public SpecificExtractor getExtractor() {
        return extractor;
    }

    public Feature<?> extract(Article article) {
        return extractor.extract(article);
    }

    public String getName() {
        return name;
    }
}
