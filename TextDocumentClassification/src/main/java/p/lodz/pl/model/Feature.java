package p.lodz.pl.model;

import lombok.Data;
import p.lodz.pl.enums.Type;

@Data
public class Feature<T> {

    private final Type type;
    private T feature;

    public Feature(Type type, T feature) {
        this.type = type;
        this.feature = feature;
    }
}
