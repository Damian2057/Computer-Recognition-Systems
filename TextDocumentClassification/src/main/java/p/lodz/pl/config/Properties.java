package p.lodz.pl.config;

import lombok.Data;

@Data
public class Properties {
    private final Features features;
    private final int longWordLength;

    public boolean isCurrency() {
        return features.isCurrency();
    }

    public boolean isNumberOfSentences() {
        return features.isNumberOfSentences();
    }
}
