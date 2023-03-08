package p.lodz.pl.model;

import lombok.Data;
import p.lodz.pl.enums.FeatureType;

@Data
public class Feature<T> {

    private final FeatureType type;
    private T feature;

    public Feature(FeatureType type, T feature) {
        this.type = type;
        this.feature = feature;
    }
}
