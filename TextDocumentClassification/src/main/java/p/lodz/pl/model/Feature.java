package p.lodz.pl.model;

import lombok.Data;
import p.lodz.pl.enums.Type;

import java.io.Serializable;

@Data
public class Feature<T> implements Serializable {

    private final Type type;
    private T feature;

    public Feature(Type type, T feature) {
        this.type = type;
        this.feature = feature;
    }

    public void setFeature(Double feature) {
        this.feature = (T) feature;
    }

    public void setFeature(String feature) {
        this.feature = (T) feature;
    }

    public Type getType() {
        return type;
    }
}
