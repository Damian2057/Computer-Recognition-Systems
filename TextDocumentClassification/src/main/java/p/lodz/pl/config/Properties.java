package p.lodz.pl.config;

import lombok.Data;

@Data
public class Properties {
    private final Features features;

    public boolean isCurrency() {
        return features.isCurrency();
    }

    public boolean isNumberOfSentences() {
        return features.isNumberOfSentences();
    }
}
