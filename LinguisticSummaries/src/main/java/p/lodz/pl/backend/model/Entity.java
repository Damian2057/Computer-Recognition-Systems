package p.lodz.pl.backend.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Entity {
    private double policyTenure;
    private double ageOfCar;
    private double ageOfPolicyHolder;
    private double populationDensity;
    private double displacement;
    private double turningRadius;
    private double length;
    private double width;
    private double height;
    private double grossWeight;
}
