package p.lodz.pl.backend.model;

import lombok.Builder;

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
) {
}
