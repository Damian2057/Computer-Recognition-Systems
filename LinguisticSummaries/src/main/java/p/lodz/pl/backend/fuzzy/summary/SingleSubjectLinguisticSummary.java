package p.lodz.pl.backend.fuzzy.summary;

import p.lodz.pl.backend.fuzzy.linguistic.Label;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SingleSubjectLinguisticSummary<R> extends AbstractLinguisticSummary {

    private final List<Label<R>> qualifiers;
    private final String subject;
    private final List<R> policies;
    private final List<Double> weights;

    private List<Summary> summaries;

    public SingleSubjectLinguisticSummary(Quantifier quantifier,
                                          List<Label<R>> qualifiers,
                                          String subject,
                                          List<R> policies,
                                          List<Double> weights) {
        super(quantifier);
        this.qualifiers = qualifiers;
        this.subject = subject;
        this.policies = policies;
        this.weights = weights;
    }

    @Override
    public List<Summary> generateSummary() {
        summaries = new ArrayList<>();
        //generateFirstForm();
        if (!quantifier.isAbsolute()) {
            generateSecondForm();
        }
        return summaries;
    }

    private void generateFirstForm() {
        final int form = 1;
        List<List<Integer>> combinations = Combiner.getFirstFormCombinations(1, qualifiers.size());

        for (List<Integer> combination : combinations) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(quantifier.getLabelName()).append(SPACE)
                    .append(subject).append(HAVE);
            int index = 0;
            for (int i : combination) {
                stringBuilder.append(qualifiers.get(i).getLabelName()).append(SPACE)
                        .append(qualifiers.get(i).getLinguisticVariableName());
                index++;
                if (index < combination.size()) {
                    stringBuilder.append(AND);
                }
            }
            summaries.add(new Summary(form, stringBuilder.toString(), getQualityForSummary()));
        }
    }

    private void generateSecondForm() {
        final int form = 2;
        List<List<Integer>> combinations = Combiner.getSecondFormCombinations(qualifiers.size());
        for (List<Integer> combination : combinations) {
            System.out.println(combination);
        }
    }

    public List<Label<R>> getQualifiers() {
        return qualifiers;
    }

    public List<R> getPolicies() {
        return policies;
    }

    private List<Double> getQualityForSummary() {
        double T1 = degreeOfTruth();
        double T2 = degreeOfImprecision();
        double T3 = degreeOfCovering();
        double T4 = degreeOfAppropriateness();
        double T5 = lengthOfSummary();
        double T6 = degreeOfQuantifierImprecision();
        double T7 = degreeOfQuantifierCardinality();
        double T8 = degreeOfSummarizerCardinality();
        double T9 = degreeOfQualifierImprecision();
        double T10 = degreeOfQualifierCardinality();
        double T11 = lengthOfQualifier();

        double avg = T1 * weights.get(0)
                + T2 * weights.get(1)
                + T3 * weights.get(2)
                + T4 * weights.get(3)
                + T5 * weights.get(4)
                + T6 * weights.get(5)
                + T7 * weights.get(6)
                + T8 * weights.get(7)
                + T9 * weights.get(8)
                + T10 * weights.get(9)
                + T11 * weights.get(10);

        List<Double> quality = new ArrayList<>();
        quality.add(avg);
        quality.add(T1);
        quality.add(T2);
        quality.add(T3);
        quality.add(T4);
        quality.add(T5);
        quality.add(T6);
        quality.add(T7);
        quality.add(T8);
        quality.add(T9);
        quality.add(T10);
        quality.add(T11);

        return quality;
    }

    private double degreeOfTruth() {
        return 0.0;
    }

    private double degreeOfImprecision() {
        return 0.0;
    }

    private double degreeOfCovering() {
        return 0.0;
    }

    private double degreeOfAppropriateness() {
        return 0.0;
    }

    private double lengthOfSummary() {
        return 0.0;
    }

    private double degreeOfQuantifierImprecision() {
        return 0.0;
    }

    private double degreeOfQuantifierCardinality() {
        return 0.0;
    }

    private double degreeOfSummarizerCardinality() {
        return 0.0;
    }

    private double degreeOfQualifierImprecision() {
        return 0.0;
    }

    private double degreeOfQualifierCardinality() {
        return 0.0;
    }

    private double lengthOfQualifier() {
        return 0.0;
    }

    private double quality() {
        return degreeOfTruth() * weights.get(0)
                + degreeOfImprecision() * weights.get(1)
                + degreeOfCovering() * weights.get(2)
                + degreeOfAppropriateness() * weights.get(3)
                + lengthOfSummary() * weights.get(4)
                + degreeOfQuantifierImprecision() * weights.get(5)
                + degreeOfQuantifierCardinality() * weights.get(6)
                + degreeOfSummarizerCardinality() * weights.get(7)
                + degreeOfQualifierImprecision() * weights.get(8)
                + degreeOfQualifierCardinality() * weights.get(9)
                + lengthOfQualifier() * weights.get(10);
    }
}
