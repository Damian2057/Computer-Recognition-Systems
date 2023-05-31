package p.lodz.pl.backend.repository;

import p.lodz.pl.backend.fuzzy.function.TrapezoidalFunction;
import p.lodz.pl.backend.fuzzy.function.TriangularFunction;
import p.lodz.pl.backend.fuzzy.function.domain.ContinuousDomain;
import p.lodz.pl.backend.fuzzy.function.domain.Domain;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticVariable;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;
import p.lodz.pl.backend.model.PolicyEntity;

import java.util.ArrayList;
import java.util.List;

public class MockRepository {

    private static final String  POLICY_TENURE = "policy tenure";
    private static final String  AGE_OF_CAR = "age of car";
    private static final String  AGE_OF_POLICYHOLDER = "age of policyholder";
    private static final String  POPULATION_DENSITY = "population density";
    private static final String  DISPLACEMENT = "engine displacement";
    private static final String  TURNING_RADIUS = "turning radius";
    private static final String  GROSS_WEIGHT = "gross weight";
    private static final String  LENGTH = "length";
    private static final String  WIDTH = "width";
    private static final String  HEIGHT = "height";

    private static final String POLICY_TENURE_TO_3_MONTHS = "policy tenure to 3 months";
    private static final String  POLICY_TENURE_HALF_YEAR = "policy tenure of around half of the year";
    private static final String POLICY_TENURE_4th_QUARTER_YEAR = "policy tenure of 4th quarter of the year";
    private static final String POLICY_TENURE_OVER_YEAR = "policy tenure of over a year";
    private static final String AGE_OF_CAR_NEW = "new car";
    private static final String AGE_OF_CAR_USED = "used car";
    private static final String AGE_OF_CAR_OLD = "old car";
    private static final String AGE_OF_CAR_VINTAGE = "vintage car";
    private static final String AGE_OF_POLICYHOLDER_YOUNG = "young adult policyholder";
    private static final String AGE_OF_POLICYHOLDER_ADULT = "adult policyholder";
    private static final String AGE_OF_POLICYHOLDER_MEDIUM = "medium aged policyholder";
    private static final String AGE_OF_POLICYHOLDER_OLD = "old policyholder";
    private static final String POPULATION_DENSITY_RURAL = "population density of rural area";
    private static final String POPULATION_DENSITY_SUBURBIA = "population density of suburbia";
    private static final String POPULATION_DENSITY_CITY = "population density of a city";
    private static final String POPULATION_DENSITY_BIG_CITY = "population density of a big city";
    private static final String POPULATION_DENSITY_METROPOLIS = "population density of a metropolis";
    private static final String ENGINE_DISPLACEMENT_LESS_900 = "engine displacement less than 900";
    private static final String ENGINE_DISPLACEMENT_FROM_950 = "engine displacement from 950 to 1100";
    private static final String ENGINE_DISPLACEMENT_ABOUT_1200 = "engine displacement of about 1200";
    private static final String ENGINE_DISPLACEMENT_MORE_1300 = "engine displacement more than 1300";
    private static final String GROSS_WEIGHT_LESS_1150 = "gross weight less than 1150 kg";
    private static final String GROSS_WEIGHT_UPTO_1300 = "gross weight up to 1300 kg";
    private static final String GROSS_WEIGHT_AROUND_1475 = "gross weight around 1475 kg";
    private static final String GROSS_WEIGHT_MORE_1600 = "gross weight more than 1600 kg";
    private static final String TURNING_RADIUS_VERY_SMALL = "very small turning radius";
    private static final String TURNING_RADIUS_SMALL = "small turning radius";
    private static final String TURNING_RADIUS_AVERAGE = "average turning radius";
    private static final String TURNING_RADIUS_BIG = "big turning radius";
    private static final String LENGTH_SMALL = "small car length";
    private static final String LENGTH_HATCHBACK = "hatchback length";
    private static final String LENGTH_SUV = "small suv length";
    private static final String LENGTH_COMBI = "combi length";
    private static final String WIDTH_CITY = "city car width";
    private static final String WIDTH_COMPACT = "compact car width";
    private static final String WIDTH_FAMILY = "family car width";
    private static final String WIDTH_PICKUP = "pick-up car width";
    private static final String HEIGHT_SPORTY = "sporty height";
    private static final String HEIGHT_CONVERTIBLE = "convertible height";
    private static final String HEIGHT_CLASSIC = "classic height";
    private static final String HEIGHT_CROSSOVER = "crossover height";

    private static final String SUBJECT1 = "cars with sporty height";
    private static final String SUBJECT2 = "cars with around half of the year policy tenure";
    private static final String SUBJECT3 = "cars with medium aged policyholders";
    private static final String SUBJECT4 = "policyholders with combi length cars";
    private static final String SUBJECT5 = "policyholders that have to 3 months policy tenure";
    private static final String SUBJECT6 = "family car width cars that have adult policyholders";
    private static final String SUBJECT7 = "policyholders who are young";

    private final List<LinguisticVariable<PolicyEntity>> linguisticVariables;
    private final List<Quantifier> quantifiers;
    private final List<String> subjects;

    public MockRepository() {
        this.linguisticVariables = getLinguisticVariables();
        this.quantifiers = getQuantifiers();
        this.subjects = getSubjects();
    }

    public List<LinguisticVariable<PolicyEntity>> findAllLinguisticVariables() {
        return linguisticVariables;
    }

    public List<Quantifier> findAllQuantifiers() {
        return quantifiers;
    }

    public List<String> findAllSubjects() {
        return subjects;
    }

    public void save(LinguisticVariable<PolicyEntity> linguisticVariable) {
        if (linguisticVariables.stream()
                .noneMatch(lv -> lv.getLinguisticVariableName().equals(linguisticVariable.getLinguisticVariableName()))) {
            linguisticVariables.add(linguisticVariable);
        } else {
            for (int i = 0; i < linguisticVariables.size(); i++) {
                if (linguisticVariables.get(i).getLinguisticVariableName().equals(linguisticVariable.getLinguisticVariableName())) {
                    linguisticVariables.set(i, linguisticVariable);
                    break;
                }
            }
        }
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

    private List<String> getSubjects() {
        List<String> subjectList = new ArrayList<>();

        subjectList.add(SUBJECT1);
        subjectList.add(SUBJECT2);
        subjectList.add(SUBJECT3);
        subjectList.add(SUBJECT4);
        subjectList.add(SUBJECT5);
        subjectList.add(SUBJECT6);
        subjectList.add(SUBJECT7);

        return subjectList;
    }

    private List<Quantifier> getQuantifiers() {
        List<Quantifier> quantifierList = new ArrayList<>();

        Domain domain = new ContinuousDomain(0, 1);
        quantifierList.add(new Quantifier("Almost none", x -> x,
                new TriangularFunction(domain, 0, 0, 0.2), false));
        quantifierList.add(new Quantifier("Some", x -> x,
                new TrapezoidalFunction(domain, 0, 0.12, 0.28, 0.48), false));
        quantifierList.add(new Quantifier("About half", x -> x,
                new TrapezoidalFunction(domain, 0.24, 0.44, 0.56, 0.76), false));
        quantifierList.add(new Quantifier("Many", x -> x,
                new TrapezoidalFunction(domain, 0.56, 0.76, 0.84, 0.96), false));
        quantifierList.add(new Quantifier("Almost all", x -> x,
                new TrapezoidalFunction(domain, 0.76, 0.96, 1.0, 1.0), false));

        Domain domain2 = new ContinuousDomain(0, 39063);
        quantifierList.add(new Quantifier("Less than 9000", x -> x,
                new TrapezoidalFunction(domain2, 0, 0, 8000, 9000), true));
        quantifierList.add(new Quantifier("About 1/4", x -> x,
                new TrapezoidalFunction(domain2, 5000, 10000, 13000, 18000), true));
        quantifierList.add(new Quantifier("About half", x -> x,
                new TrapezoidalFunction(domain2, 12000, 17000, 20000, 23000), true));
        quantifierList.add(new Quantifier("Over 20000", x -> x,
                new TrapezoidalFunction(domain2, 19000, 22000, 25000, 28000), true));
        quantifierList.add(new Quantifier("About 3/4", x -> x,
                new TrapezoidalFunction(domain2, 25000, 27000, 29000, 34000), true));
        quantifierList.add(new Quantifier("More than 3/4", x -> x,
                new TrapezoidalFunction(domain2, 30000, 31000, 39064, 39064), true));

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
        Domain domain = new ContinuousDomain(0.0, 1.39);
        policyTenureLabels.add(new LinguisticLabel<>(POLICY_TENURE, "to 3 months", PolicyEntity::policyTenure,
                new TrapezoidalFunction(domain, 0, 0, 0.28, 0.35)));
        policyTenureLabels.add(new LinguisticLabel<>(POLICY_TENURE, "around half of the year", PolicyEntity::policyTenure,
                new TrapezoidalFunction(domain, 0.21, 0.42, 0.63, 0.77)));
        policyTenureLabels.add(new LinguisticLabel<>(POLICY_TENURE, "4th quarter of the year", PolicyEntity::policyTenure,
                new TrapezoidalFunction(domain, 0.63, 0.77, 0.98, 1.05)));
        policyTenureLabels.add(new LinguisticLabel<>(POLICY_TENURE, "over a year", PolicyEntity::policyTenure,
                new TrapezoidalFunction(domain, 0.98, 1.05, 1.39, 1.39)));

        return policyTenureLabels;
    }

    private List<LinguisticLabel<PolicyEntity>> getAgeOfCarLabels() {
        List<LinguisticLabel<PolicyEntity>> ageOfCarLabels = new ArrayList<>();
        Domain domain = new ContinuousDomain(1995, 2020);
        ageOfCarLabels.add(new LinguisticLabel<>(AGE_OF_CAR, "vintage", PolicyEntity::ageOfCar,
                new TrapezoidalFunction(domain, 1995, 1995, 1998, 2000)));
        ageOfCarLabels.add(new LinguisticLabel<>(AGE_OF_CAR, "old", PolicyEntity::ageOfCar,
                new TrapezoidalFunction(domain, 1998, 2000, 2005, 2008)));
        ageOfCarLabels.add(new LinguisticLabel<>(AGE_OF_CAR, "used", PolicyEntity::ageOfCar,
                new TrapezoidalFunction(domain, 2005, 2007, 2013, 2016)));
        ageOfCarLabels.add(new LinguisticLabel<>(AGE_OF_CAR, "new", PolicyEntity::ageOfCar,
                new TrapezoidalFunction(domain, 2014, 2015, 2020, 2020)));

        return ageOfCarLabels;
    }

    private List<LinguisticLabel<PolicyEntity>> getAgeOfPolicyholderLabels() {
        List<LinguisticLabel<PolicyEntity>> ageOfPolicyholders = new ArrayList<>();
        Domain domain = new ContinuousDomain(18, 62);
        ageOfPolicyholders.add(new LinguisticLabel<>(AGE_OF_POLICYHOLDER, "young adult", PolicyEntity::ageOfPolicyHolder,
                new TrapezoidalFunction(domain, 18, 18, 20, 24)));
        ageOfPolicyholders.add(new LinguisticLabel<>(AGE_OF_POLICYHOLDER, "adult", PolicyEntity::ageOfPolicyHolder,
                new TrapezoidalFunction(domain, 22, 26, 38, 44)));
        ageOfPolicyholders.add(new LinguisticLabel<>(AGE_OF_POLICYHOLDER, "medium age", PolicyEntity::ageOfPolicyHolder,
                new TrapezoidalFunction(domain, 40, 42, 54, 58)));
        ageOfPolicyholders.add(new LinguisticLabel<>(AGE_OF_POLICYHOLDER, "old", PolicyEntity::ageOfPolicyHolder,
                new TrapezoidalFunction(domain, 56, 58, 62, 62)));

        return  ageOfPolicyholders;
    }

    private List<LinguisticLabel<PolicyEntity>> getPopulationDensityLabels() {
        List<LinguisticLabel<PolicyEntity>> populationDensities = new ArrayList<>();
        Domain domain = new ContinuousDomain(290, 73400);
        populationDensities.add(new LinguisticLabel<>(POPULATION_DENSITY, "rural area", PolicyEntity::populationDensity,
                new TriangularFunction(domain, 290, 290, 3950)));
        populationDensities.add(new LinguisticLabel<>(POPULATION_DENSITY, "suburbia", PolicyEntity::populationDensity,
                new TriangularFunction(domain, 290, 7610, 14930)));
        populationDensities.add(new LinguisticLabel<>(POPULATION_DENSITY, "city", PolicyEntity::populationDensity,
                new TrapezoidalFunction(domain, 7610, 14930, 22250, 29750)));
        populationDensities.add(new LinguisticLabel<>(POPULATION_DENSITY, "big city", PolicyEntity::populationDensity,
                new TrapezoidalFunction(domain, 22250, 29750, 44210, 55190)));
        populationDensities.add(new LinguisticLabel<>(POPULATION_DENSITY, "metropolis", PolicyEntity::populationDensity,
                new TrapezoidalFunction(domain, 47870, 51530, 73400, 73400)));

        return populationDensities;
    }

    private List<LinguisticLabel<PolicyEntity>> getDisplacementLabels() {
        List<LinguisticLabel<PolicyEntity>> displacements = new ArrayList<>();
        Domain domain = new ContinuousDomain(796, 1498);
        displacements.add(new LinguisticLabel<>(DISPLACEMENT, "less than 900", PolicyEntity::displacement,
                new TrapezoidalFunction(domain, 796, 796, 896, 946)));
        displacements.add(new LinguisticLabel<>(DISPLACEMENT, "from 950 to 1100", PolicyEntity::displacement,
                new TrapezoidalFunction(domain, 896, 946, 1096, 1146)));
        displacements.add(new LinguisticLabel<>(DISPLACEMENT, "about 1225", PolicyEntity::displacement,
                new TrapezoidalFunction(domain, 1096, 1146, 1246, 1346)));
        displacements.add(new LinguisticLabel<>(DISPLACEMENT, "more than 1350", PolicyEntity::displacement,
                new TrapezoidalFunction(domain, 1246, 1346, 1498, 1498)));

        return displacements;
    }

    private List<LinguisticLabel<PolicyEntity>> getGrossWeightLabels() {
        List<LinguisticLabel<PolicyEntity>> grossWeights = new ArrayList<>();
        Domain domain = new ContinuousDomain(1051, 1720);
        grossWeights.add(new LinguisticLabel<>(GROSS_WEIGHT, "less than 1150 kg", PolicyEntity::grossWeight,
                new TrapezoidalFunction(domain, 1051, 1051, 1135, 1219)));
        grossWeights.add(new LinguisticLabel<>(GROSS_WEIGHT, "up to 1300 kg", PolicyEntity::grossWeight,
                new TrapezoidalFunction(domain, 1051, 1261, 1303, 1345)));
        grossWeights.add(new LinguisticLabel<>(GROSS_WEIGHT, "around 1475 kg", PolicyEntity::grossWeight,
                new TrapezoidalFunction(domain, 1261, 1387, 1513, 1597)));
        grossWeights.add(new LinguisticLabel<>(GROSS_WEIGHT, "more than 1600 kg", PolicyEntity::grossWeight,
                new TrapezoidalFunction(domain, 1513, 1597, 1720, 1720)));

        return grossWeights;
    }

    private List<LinguisticLabel<PolicyEntity>> getTurningRadiusLabels() {
        List<LinguisticLabel<PolicyEntity>> turningRadiuses = new ArrayList<>();
        Domain domain = new ContinuousDomain(4.50, 5.20);
        turningRadiuses.add(new LinguisticLabel<>(TURNING_RADIUS, "very small", PolicyEntity::turningRadius,
                new TrapezoidalFunction(domain, 4.50, 4.50, 4.60, 4.65)));
        turningRadiuses.add(new LinguisticLabel<>(TURNING_RADIUS, "small", PolicyEntity::turningRadius,
                new TrapezoidalFunction(domain, 4.60, 4.70, 4.85, 4.90)));
        turningRadiuses.add(new LinguisticLabel<>(TURNING_RADIUS, "average", PolicyEntity::turningRadius,
                new TriangularFunction(domain, 4.75, 5.00, 5.10)));
        turningRadiuses.add(new LinguisticLabel<>(TURNING_RADIUS, "big", PolicyEntity::turningRadius,
                new TrapezoidalFunction(domain, 5.00, 5.10, 5.20, 5.20)));

        return turningRadiuses;
    }

    private List<LinguisticLabel<PolicyEntity>> getLengthLabels() {
        List<LinguisticLabel<PolicyEntity>> legths = new ArrayList<>();
        Domain domain = new ContinuousDomain(3445, 4300);
        legths.add(new LinguisticLabel<>(LENGTH, "small car", PolicyEntity::length,
                new TrapezoidalFunction(domain, 3445, 3445, 3580, 3670)));
        legths.add(new LinguisticLabel<>(LENGTH, "hatchback", PolicyEntity::length,
                new TrapezoidalFunction(domain, 3580, 3670, 3850, 3985)));
        legths.add(new LinguisticLabel<>(LENGTH, "small suv", PolicyEntity::length,
                new TrapezoidalFunction(domain, 3895,3940, 4075, 4165)));
        legths.add(new LinguisticLabel<>(LENGTH, "combi", PolicyEntity::length,
                new TrapezoidalFunction(domain, 4075, 4165, 4300, 4300)));

        return legths;
    }

    private List<LinguisticLabel<PolicyEntity>> getWidthLabels() {
        List<LinguisticLabel<PolicyEntity>> widths = new ArrayList<>();
        Domain domain = new ContinuousDomain(1475, 1811);
        widths.add(new LinguisticLabel<>(WIDTH, "city car", PolicyEntity::width,
                new TrapezoidalFunction(domain, 1475, 1475, 1496, 1559)));
        widths.add(new LinguisticLabel<>(WIDTH, "compact car", PolicyEntity::width,
                new TrapezoidalFunction(domain, 1517, 1538, 1622, 1685)));
        widths.add(new LinguisticLabel<>(WIDTH, "family car", PolicyEntity::width,
                new TrapezoidalFunction(domain, 1622, 1664, 1727, 1790)));
        widths.add(new LinguisticLabel<>(WIDTH, "pick-up", PolicyEntity::width,
                new TrapezoidalFunction(domain, 1748, 1769, 1811, 1811)));

        return widths;
    }

    private List<LinguisticLabel<PolicyEntity>> getHeightLabels() {
        List<LinguisticLabel<PolicyEntity>> heights = new ArrayList<>();
        Domain domain = new ContinuousDomain(1475, 1825);
        heights.add(new LinguisticLabel<>(HEIGHT, "sporty", PolicyEntity::height,
                new TrapezoidalFunction(domain, 1475, 1475, 1500, 1525)));
        heights.add(new LinguisticLabel<>(HEIGHT, "convertible", PolicyEntity::height,
                new TriangularFunction(domain, 1475, 1550, 1625)));
        heights.add(new LinguisticLabel<>(HEIGHT, "classic", PolicyEntity::height,
                new TrapezoidalFunction(domain, 1575, 1625, 1725, 1800)));
        heights.add(new LinguisticLabel<>(HEIGHT, "crossover", PolicyEntity::height,
                new TrapezoidalFunction(domain, 1750, 1775, 1825, 1825)));

        return heights;
    }
}
