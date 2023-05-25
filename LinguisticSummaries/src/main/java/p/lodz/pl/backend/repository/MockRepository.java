package p.lodz.pl.backend.repository;

import p.lodz.pl.backend.fuzzy.function.TrapezoidalFunction;
import p.lodz.pl.backend.fuzzy.function.TriangularFunction;
import p.lodz.pl.backend.fuzzy.function.domain.ContinuousDomain;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticVariable;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;
import p.lodz.pl.backend.model.PolicyEntity;

import java.util.ArrayList;
import java.util.List;

public class MockRepository {

    private static final String  POLICY_TENURE = "PolicyTenure";
    private static final String  AGE_OF_CAR = "AgeOfCar";
    private static final String  AGE_OF_POLICYHOLDER = "AgeOfPolicyholder";
    private static final String  POPULATION_DENSITY = "PopulationDensity";
    private static final String  DISPLACEMENT = "Displacement";
    private static final String  TURNING_RADIUS = "TurningRadius";
    private static final String  GROSS_WEIGHT = "GrossWeight";
    private static final String  LENGTH = "Length";
    private static final String  WIDTH = "Width";
    private static final String  HEIGHT = "Height";

    private final List<LinguisticVariable<PolicyEntity>> linguisticVariables;
    private final List<Quantifier> quantifiers;

    public MockRepository() {
        this.linguisticVariables = getLinguisticVariables();
        this.quantifiers = getQuantifiers();
    }

    public List<LinguisticVariable<PolicyEntity>> findAllLinguisticVariables() {
        return linguisticVariables;
    }

    public List<Quantifier> findAllQuantifiers() {
        return quantifiers;
    }

    public void save(LinguisticVariable<PolicyEntity> linguisticVariable) {
        linguisticVariables.add(linguisticVariable);
    }

    public void save(Quantifier quantifier) {
        quantifiers.add(quantifier);
    }

    public void delete(LinguisticVariable<PolicyEntity> linguisticVariable) {
        linguisticVariables.remove(linguisticVariable);
    }

    public void delete(Quantifier quantifier) {
        quantifiers.remove(quantifier);
    }

    public LinguisticVariable<PolicyEntity> findLinguisticVariableByName(String name) {
        return linguisticVariables.stream()
                .filter(linguisticVariable -> linguisticVariable.getLinguisticVariableName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public Quantifier findQuantifierByName(String name) {
        return quantifiers.stream()
                .filter(quantifier -> quantifier.getLabelName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public LinguisticLabel<PolicyEntity> findLinguisticLabelByName(String name) {
        return linguisticVariables.stream()
                .flatMap(linguisticVariable -> linguisticVariable.getLabels().stream())
                .filter(label -> label.getLabelName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private List<Quantifier> getQuantifiers() {
        List<Quantifier> quantifierList = new ArrayList<>();

        quantifierList.add(new Quantifier("Almost none", x -> x,
                new TrapezoidalFunction(new ContinuousDomain(0, 0.2), 0, 0, 0.12, 0.2), false));
        quantifierList.add(new Quantifier("Some", x -> x,
                new TrapezoidalFunction(new ContinuousDomain(0.16, 0.44), 0.16, 0.2, 0.4, 0.44), false));
        quantifierList.add(new Quantifier("About half", x -> x,
                new TrapezoidalFunction(new ContinuousDomain(0.4, 0.64), 0.4, 0.44, 0.6, 0.64), false));
        quantifierList.add(new Quantifier("Many", x -> x,
                new TrapezoidalFunction(new ContinuousDomain(0.6, 0.92), 0.6, 0.64, 0.88, 0.92), false));
        quantifierList.add(new Quantifier("Almost all", x -> x,
                new TrapezoidalFunction(new ContinuousDomain(0.88, 1.0), 0.88, 0.92, 1.0, 1.0), false));

        quantifierList.add(new Quantifier("Less than 9000", x -> x,
                new TrapezoidalFunction(new ContinuousDomain(0, 9000), 0, 0, 4000, 9000), true));
        quantifierList.add(new Quantifier("About 1/4", x -> x,
                new TriangularFunction(new ContinuousDomain(5000, 15000), 5000, 10000, 15000), true));
        quantifierList.add(new Quantifier("About half", x -> x,
                new TrapezoidalFunction(new ContinuousDomain(14000, 22000), 14000, 19000, 20000, 22000), true));
        quantifierList.add(new Quantifier("Over 20000", x -> x,
                new TriangularFunction(new ContinuousDomain(20000, 27000), 20000, 25000, 27000), true));
        quantifierList.add(new Quantifier("About 3/4", x -> x,
                new TrapezoidalFunction(new ContinuousDomain(26000, 35000), 26000, 28000, 30000, 35000), true));
        quantifierList.add(new Quantifier("More than 3/4", x -> x,
                new TrapezoidalFunction(new ContinuousDomain(34000, 39064), 34000, 36000, 39064, 39064), true));

        return quantifierList;
    }

    private List<LinguisticVariable<PolicyEntity>> getLinguisticVariables() {
        List<LinguisticVariable<PolicyEntity>> linguisticVariableList = new ArrayList<>();

        linguisticVariableList.add(new LinguisticVariable<>(POLICY_TENURE, getPolicyTenureLabels()));
        linguisticVariableList.add(new LinguisticVariable<>(AGE_OF_CAR, getAgeOfCarLabels()));
        linguisticVariableList.add(new LinguisticVariable<>(AGE_OF_POLICYHOLDER, getAgeOfPolicyholderLabels()));
        linguisticVariableList.add(new LinguisticVariable<>(POPULATION_DENSITY, getPopulationDensityLabels()));
        linguisticVariableList.add(new LinguisticVariable<>(DISPLACEMENT, getDisplacementLabels()));
        linguisticVariableList.add(new LinguisticVariable<>(TURNING_RADIUS, getTurningRadiusLabels()));
        linguisticVariableList.add(new LinguisticVariable<>(GROSS_WEIGHT, getGrossWeightLabels()));
        linguisticVariableList.add(new LinguisticVariable<>(LENGTH, getLengthLabels()));
        linguisticVariableList.add(new LinguisticVariable<>(WIDTH, getWidthLabels()));
        linguisticVariableList.add(new LinguisticVariable<>(HEIGHT, getHeightLabels()));

        return linguisticVariableList;
    }

    private List<LinguisticLabel<PolicyEntity>> getPolicyTenureLabels() {
        List<LinguisticLabel<PolicyEntity>> policyTenureLabels = new ArrayList<>();

        policyTenureLabels.add(new LinguisticLabel<>(POLICY_TENURE, "to 3 months", PolicyEntity::policyTenure,
                new TrapezoidalFunction(new ContinuousDomain(0, 0.35), 0, 0, 0.28, 0.35)));
        policyTenureLabels.add(new LinguisticLabel<>(POLICY_TENURE, "around half of the year", PolicyEntity::policyTenure,
                new TrapezoidalFunction(new ContinuousDomain(0.21, 0.77), 0.21, 0.42, 0.63, 0.77)));
        policyTenureLabels.add(new LinguisticLabel<>(POLICY_TENURE, "4th quarter of the year", PolicyEntity::policyTenure,
                new TrapezoidalFunction(new ContinuousDomain(0.63, 1.05), 0.63, 0.77, 0.98, 1.05)));
        policyTenureLabels.add(new LinguisticLabel<>(POLICY_TENURE, "over a year", PolicyEntity::policyTenure,
                new TrapezoidalFunction(new ContinuousDomain(0.98, 1.39), 0.98, 1.05, 1.39, 1.39)));

        return policyTenureLabels;
    }

    private List<LinguisticLabel<PolicyEntity>> getAgeOfCarLabels() {
        List<LinguisticLabel<PolicyEntity>> ageOfCarLabels = new ArrayList<>();

        ageOfCarLabels.add(new LinguisticLabel<>(AGE_OF_CAR, "Vintage", PolicyEntity::ageOfCar,
                new TrapezoidalFunction(new ContinuousDomain(1995, 2000), 1995, 1995, 1998, 2000)));
        ageOfCarLabels.add(new LinguisticLabel<>(AGE_OF_CAR, "Old", PolicyEntity::ageOfCar,
                new TrapezoidalFunction(new ContinuousDomain(1998, 2008), 1998, 2000, 2005, 2008)));
        ageOfCarLabels.add(new LinguisticLabel<>(AGE_OF_CAR, "Used", PolicyEntity::ageOfCar,
                new TrapezoidalFunction(new ContinuousDomain(2005, 2016), 2005, 2007, 2013, 2016)));
        ageOfCarLabels.add(new LinguisticLabel<>(AGE_OF_CAR, "New", PolicyEntity::ageOfCar,
                new TrapezoidalFunction(new ContinuousDomain(2014, 2020), 2014, 2015, 2020, 2020)));

        return ageOfCarLabels;
    }

    private List<LinguisticLabel<PolicyEntity>> getAgeOfPolicyholderLabels() {
        List<LinguisticLabel<PolicyEntity>> ageOfPolicyholders = new ArrayList<>();

        ageOfPolicyholders.add(new LinguisticLabel<>(AGE_OF_POLICYHOLDER, "Young adult", PolicyEntity::ageOfPolicyHolder,
                new TrapezoidalFunction(new ContinuousDomain(18, 24), 18, 18, 20, 24)));
        ageOfPolicyholders.add(new LinguisticLabel<>(AGE_OF_POLICYHOLDER, "Adult", PolicyEntity::ageOfPolicyHolder,
                new TrapezoidalFunction(new ContinuousDomain(22, 44), 22, 26, 38, 44)));
        ageOfPolicyholders.add(new LinguisticLabel<>(AGE_OF_POLICYHOLDER, "Medium age", PolicyEntity::ageOfPolicyHolder,
                new TrapezoidalFunction(new ContinuousDomain(40, 58), 40, 42, 54, 58)));
        ageOfPolicyholders.add(new LinguisticLabel<>(AGE_OF_POLICYHOLDER, "Old", PolicyEntity::ageOfPolicyHolder,
                new TrapezoidalFunction(new ContinuousDomain(56, 62), 56, 58, 62, 62)));

        return  ageOfPolicyholders;
    }

    private List<LinguisticLabel<PolicyEntity>> getPopulationDensityLabels() {
        List<LinguisticLabel<PolicyEntity>> populationDensities = new ArrayList<>();

        populationDensities.add(new LinguisticLabel<>(POPULATION_DENSITY, "Rural area", PolicyEntity::populationDensity,
                new TriangularFunction(new ContinuousDomain(290, 3950), 290, 290, 3950)));
        populationDensities.add(new LinguisticLabel<>(POPULATION_DENSITY, "Suburbia", PolicyEntity::populationDensity,
                new TriangularFunction(new ContinuousDomain(290, 14930), 290, 7610, 14930)));
        populationDensities.add(new LinguisticLabel<>(POPULATION_DENSITY, "City", PolicyEntity::populationDensity,
                new TrapezoidalFunction(new ContinuousDomain(7610, 29570), 7610, 14930, 22250, 29750)));
        populationDensities.add(new LinguisticLabel<>(POPULATION_DENSITY, "Big city", PolicyEntity::populationDensity,
                new TrapezoidalFunction(new ContinuousDomain(22250, 55190), 22250, 29750, 44210, 55190)));
        populationDensities.add(new LinguisticLabel<>(POPULATION_DENSITY, "Metropolis", PolicyEntity::populationDensity,
                new TrapezoidalFunction(new ContinuousDomain(47870, 73400), 47870, 51530, 73400, 73400)));

        return populationDensities;
    }

    private List<LinguisticLabel<PolicyEntity>> getDisplacementLabels() {
        List<LinguisticLabel<PolicyEntity>> displacements = new ArrayList<>();

        displacements.add(new LinguisticLabel<>(DISPLACEMENT, "Less than 900", PolicyEntity::displacement,
                new TrapezoidalFunction(new ContinuousDomain(796, 946), 796, 796, 896, 946)));
        displacements.add(new LinguisticLabel<>(DISPLACEMENT, "From 950 to 1100", PolicyEntity::displacement,
                new TrapezoidalFunction(new ContinuousDomain(896, 1146), 896, 946, 1096, 1146)));
        displacements.add(new LinguisticLabel<>(DISPLACEMENT, "About 1225", PolicyEntity::displacement,
                new TrapezoidalFunction(new ContinuousDomain(1096, 1346), 1096, 1146, 1246, 1346)));
        displacements.add(new LinguisticLabel<>(DISPLACEMENT, "More than 1350", PolicyEntity::displacement,
                new TrapezoidalFunction(new ContinuousDomain(1246, 1498), 1246, 1346, 1498, 1498)));

        return displacements;
    }

    private List<LinguisticLabel<PolicyEntity>> getGrossWeightLabels() {
        List<LinguisticLabel<PolicyEntity>> grossWeights = new ArrayList<>();

        grossWeights.add(new LinguisticLabel<>(GROSS_WEIGHT, "Less than 1150 kg", PolicyEntity::grossWeight,
                new TrapezoidalFunction(new ContinuousDomain(1051, 1219), 1051, 1051, 1135, 1219)));
        grossWeights.add(new LinguisticLabel<>(GROSS_WEIGHT, "Up to 1300 kg", PolicyEntity::grossWeight,
                new TrapezoidalFunction(new ContinuousDomain(1051, 1345), 1051, 1261, 1303, 1345)));
        grossWeights.add(new LinguisticLabel<>(GROSS_WEIGHT, "Around 1475 kg", PolicyEntity::grossWeight,
                new TrapezoidalFunction(new ContinuousDomain(1261, 1597), 1261, 1387, 1513, 1597)));
        grossWeights.add(new LinguisticLabel<>(GROSS_WEIGHT, "More than 1600 kg", PolicyEntity::grossWeight,
                new TrapezoidalFunction(new ContinuousDomain(1513, 1720), 1513, 1597, 1720, 1720)));

        return grossWeights;
    }

    private List<LinguisticLabel<PolicyEntity>> getTurningRadiusLabels() {
        List<LinguisticLabel<PolicyEntity>> turningRadiuses = new ArrayList<>();

        turningRadiuses.add(new LinguisticLabel<>(TURNING_RADIUS, "Very small", PolicyEntity::turningRadius,
                new TrapezoidalFunction(new ContinuousDomain(4.50, 4.65), 4.50, 4.50, 4.60, 4.65)));
        turningRadiuses.add(new LinguisticLabel<>(TURNING_RADIUS, "Small", PolicyEntity::turningRadius,
                new TrapezoidalFunction(new ContinuousDomain(4.60, 4.90), 4.60, 4.70, 4.85, 4.90)));
        turningRadiuses.add(new LinguisticLabel<>(TURNING_RADIUS, "Average", PolicyEntity::turningRadius,
                new TriangularFunction(new ContinuousDomain(4.75, 5.10), 4.75, 5.00, 5.10)));
        turningRadiuses.add(new LinguisticLabel<>(TURNING_RADIUS, "Big", PolicyEntity::turningRadius,
                new TrapezoidalFunction(new ContinuousDomain(5.00, 5.20), 5.00, 5.10, 5.20, 5.20)));

        return turningRadiuses;
    }

    private List<LinguisticLabel<PolicyEntity>> getLengthLabels() {
        List<LinguisticLabel<PolicyEntity>> legths = new ArrayList<>();

        legths.add(new LinguisticLabel<>(LENGTH, "Small car", PolicyEntity::length,
                new TrapezoidalFunction(new ContinuousDomain(3445, 3670), 3445, 3445, 3580, 3670)));
        legths.add(new LinguisticLabel<>(LENGTH, "Hatchback", PolicyEntity::length,
                new TrapezoidalFunction(new ContinuousDomain(3580, 3985), 3580, 3670, 3850, 3985)));
        legths.add(new LinguisticLabel<>(LENGTH, "Small suv", PolicyEntity::length,
                new TrapezoidalFunction(new ContinuousDomain(3895, 4165), 3895,3940, 4075, 4165)));
        legths.add(new LinguisticLabel<>(LENGTH, "Combi", PolicyEntity::length,
                new TrapezoidalFunction(new ContinuousDomain(4075, 4300), 4075, 4165, 4300, 4300)));

        return legths;
    }

    private List<LinguisticLabel<PolicyEntity>> getWidthLabels() {
        List<LinguisticLabel<PolicyEntity>> widths = new ArrayList<>();

        widths.add(new LinguisticLabel<>(WIDTH, "City car", PolicyEntity::width,
                new TrapezoidalFunction(new ContinuousDomain(1475, 1559), 1475, 1475, 1496, 1559)));
        widths.add(new LinguisticLabel<>(WIDTH, "Compact car", PolicyEntity::width,
                new TrapezoidalFunction(new ContinuousDomain(1517, 1685), 1517, 1538, 1622, 1685)));
        widths.add(new LinguisticLabel<>(WIDTH, "Family car", PolicyEntity::width,
                new TrapezoidalFunction(new ContinuousDomain(1622, 1790), 1622, 1664, 1727, 1790)));
        widths.add(new LinguisticLabel<>(WIDTH, "Pick-up", PolicyEntity::width,
                new TrapezoidalFunction(new ContinuousDomain(1748, 1811), 1748, 1769, 1811, 1811)));

        return widths;
    }

    private List<LinguisticLabel<PolicyEntity>> getHeightLabels() {
        List<LinguisticLabel<PolicyEntity>> heights = new ArrayList<>();

        heights.add(new LinguisticLabel<>(HEIGHT, "Sporty", PolicyEntity::height,
                new TrapezoidalFunction(new ContinuousDomain(1475, 1525), 1475, 1475, 1500, 1525)));
        heights.add(new LinguisticLabel<>(HEIGHT, "Convertible", PolicyEntity::height,
                new TriangularFunction(new ContinuousDomain(1475, 1625), 1475, 1550, 1625)));
        heights.add(new LinguisticLabel<>(HEIGHT, "Classic", PolicyEntity::height,
                new TrapezoidalFunction(new ContinuousDomain(1575, 1800), 1575, 1625, 1725, 1800)));
        heights.add(new LinguisticLabel<>(HEIGHT, "Crossover", PolicyEntity::height,
                new TrapezoidalFunction(new ContinuousDomain(1750, 1825), 1750, 1775, 1825, 1825)));

        return heights;
    }
}
