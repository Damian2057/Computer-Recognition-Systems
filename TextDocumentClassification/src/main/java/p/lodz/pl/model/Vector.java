package p.lodz.pl.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Vector {

    private List<Feature<?>> features;
    private String articleRealCountry;
    private String classificationCountry;

    public Vector(String country) {
        this.articleRealCountry = country;
        this.features = new ArrayList<>();
    }

    public void addFeature(Feature<?> feature) {
        this.features.add(feature);
    }
}
