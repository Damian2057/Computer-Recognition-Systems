package p.lodz.pl.logic.classifier;

import lombok.Data;

@Data
public class Neighbor {

    private final String country;
    private final double distance;

    public Neighbor(String country, double distance) {
        this.country = country;
        this.distance = distance;
    }
}
