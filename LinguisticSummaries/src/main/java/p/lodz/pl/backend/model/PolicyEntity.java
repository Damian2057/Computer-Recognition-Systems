package p.lodz.pl.backend.model;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record PolicyEntity(
        String policyId,
        double policyTenure,
        double ageOfCar,
        double ageOfPolicyHolder,
        double populationDensity,
        double displacement,
        double turningRadius,
        double length,
        double width,
        double height,
        double grossWeight
) implements Serializable {
}
