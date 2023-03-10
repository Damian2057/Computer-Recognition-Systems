package p.lodz.pl.config;

import lombok.Data;

@Data
public class Properties {
    private final Features features;
    private final int longWordLength;
    private final int numberOfOccurrences;
    private final double[] proportionOfDataSets;
    private final String regex;
    private final boolean testMode;

    public boolean isCurrencyExtractor() {
        return features.isCurrencyExtractor();
    }

    public boolean isNumberOfSentencesExtractor() {
        return features.isNumberOfSentencesExtractor();
    }

    public boolean isHistoricalFigureExtractor() {
        return features.isHistoricalFigureExtractor();
    }

    public boolean isPlacesExtractor() {
        return features.isPlacesExtractor();
    }

    public boolean isCountryExtractor() {
        return features.isCountryExtractor();
    }

    public boolean isFrequencyUniquenessWordsExtractor() {
        return features.isFrequencyUniquenessWordsExtractor();
    }

    public boolean isNumberOfLongWordsExtractor() {
        return features.isNumberOfLongWordsExtractor();
    }

    public boolean isAverageWordLengthExtractor() {
        return features.isAverageWordLengthExtractor();
    }

    public boolean isKeyWordExtractor() {
        return features.isKeyWordExtractor();
    }

    public boolean isExceptKeyWordExtractor() {
        return features.isExceptKeyWordExtractor();
    }

    public boolean isFrequencyCommonWordExtractor() {
        return features.isFrequencyCommonWordExtractor();
    }

    public boolean isCapitalWordExtractor() {
        return features.isCapitalWordExtractor();
    }

    public boolean isDocumentLengthExtractor() {
        return features.isDocumentLengthExtractor();
    }
}
