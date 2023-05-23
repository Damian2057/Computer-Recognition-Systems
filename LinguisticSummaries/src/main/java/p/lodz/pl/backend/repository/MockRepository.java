package p.lodz.pl.backend.repository;

import p.lodz.pl.backend.fuzzy.function.TrapezoidalFunction;
import p.lodz.pl.backend.fuzzy.function.domain.ContinuousDomain;
import p.lodz.pl.backend.fuzzy.linguistic.Label;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticVariable;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;
import p.lodz.pl.backend.model.PolicyEntity;

import java.util.ArrayList;
import java.util.List;

public class MockRepository {

    private static final String  POLICY_TENURE = "PolicyTenure";
    private static final String AGE = "Age";

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

    public Label<PolicyEntity> findLinguisticLabelByName(String name) {
        return linguisticVariables.stream()
                .flatMap(linguisticVariable -> linguisticVariable.getLabels().stream())
                .filter(label -> label.getLabelName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private List<Quantifier> getQuantifiers() {
        List<Quantifier> quantifierList = new ArrayList<>();

        quantifierList.add(new Quantifier("Almost none", x -> x,
                new TrapezoidalFunction(new ContinuousDomain(0, 0.2), 0, 0, 0.12, 0.2), true));

        return quantifierList;
    }

    private List<LinguisticVariable<PolicyEntity>> getLinguisticVariables() {
        List<LinguisticVariable<PolicyEntity>> linguisticVariableList = new ArrayList<>();

        linguisticVariableList.add(new LinguisticVariable<>(POLICY_TENURE, getPolicyTenureLabels()));
        linguisticVariableList.add(new LinguisticVariable<>(AGE, getAgeLabels()));

        return linguisticVariableList;
    }

    private List<Label<PolicyEntity>> getAgeLabels() {
        //EXAMPLE
        List<Label<PolicyEntity>> ageLabels = new ArrayList<>();

        ageLabels.add(new Label<>(AGE, "Young", PolicyEntity::ageOfCar, null));
        ageLabels.add(new Label<>(AGE, "Middle", PolicyEntity::ageOfCar, null));
        ageLabels.add(new Label<>(AGE, "Old", PolicyEntity::ageOfCar, null));

        return ageLabels;
    }

    private List<Label<PolicyEntity>> getPolicyTenureLabels() {
        List<Label<PolicyEntity>> policyTenureLabels = new ArrayList<>();

        policyTenureLabels.add(new Label<>(POLICY_TENURE, "to 3 months", PolicyEntity::policyTenure,
                new TrapezoidalFunction(new ContinuousDomain(0, 0.35), 0, 0, 0.28, 0.35)));
        policyTenureLabels.add(new Label<>(POLICY_TENURE, "around half of the year", PolicyEntity::policyTenure,
                new TrapezoidalFunction(new ContinuousDomain(0.21, 0.77), 0.21, 0.42, 0.63, 0.77)));
        policyTenureLabels.add(new Label<>(POLICY_TENURE, "4th quarter of the year", PolicyEntity::policyTenure,
                new TrapezoidalFunction(new ContinuousDomain(0.63, 1.05), 0.63, 0.77, 0.98, 1.05)));
        policyTenureLabels.add(new Label<>(POLICY_TENURE, "over a year", PolicyEntity::policyTenure,
                new TrapezoidalFunction(new ContinuousDomain(0.98, 1.39), 0.98, 1.05, 1.39, 1.39)));

        return policyTenureLabels;
    }
}
