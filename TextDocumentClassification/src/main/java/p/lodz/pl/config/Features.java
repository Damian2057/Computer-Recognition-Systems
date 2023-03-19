package p.lodz.pl.config;

import lombok.Data;

@Data
public class Features {

    private final boolean currencyExtractor;
    private final boolean numberOfSentencesExtractor;
    private final boolean historicalFigureExtractor;
    private final boolean placesExtractor;
    private final boolean countryExtractor;
    private final boolean frequencyUniquenessWordsExtractor;
    private final boolean numberOfLongWordsExtractor;
    private final boolean averageWordLengthExtractor;
    private final boolean keyWordExtractor;
    private final boolean exceptKeyWordExtractor;
    private final boolean frequencyCommonWordExtractor;
    private final boolean capitalWordExtractor;
    private final boolean documentLengthExtractor;
}
