package p.lodz.pl.model;

import lombok.Data;

import java.util.List;

@Data
public class Vector {
    private List<Feature<?>> features;

    public Vector(List<Feature<?>> features) {
        this.features = features;
    }
}
