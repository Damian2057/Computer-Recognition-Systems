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
        generateFirstForm();
        generateSecondForm();
        return summaries;
    }

    private void generateFirstForm() {
        final int form = 1;
        //Q1 P1 have Q#
        for (Label<R> qualifier : qualifiers) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(quantifier.getLabelName()).append(SPACE)
                            .append(subject).append(HAVE)
                            .append(qualifier.getLabelName()).append(SPACE)
                            .append(qualifier.getLinguisticVariableName());
            summaries.add(new Summary(form, stringBuilder.toString(), getQualityForSummary()));
        }
        //Q1 P1 have Q# and Q#
        for (int i = 0; i < qualifiers.size(); i++) {
            for (int j = i; j < qualifiers.size(); j++) {
                if (i != j) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(quantifier.getLabelName()).append(SPACE)
                            .append(subject).append(HAVE)
                            .append(qualifiers.get(i).getLabelName()).append(SPACE)
                            .append(qualifiers.get(i).getLinguisticVariableName())
                            .append(AND)
                            .append(qualifiers.get(j).getLabelName()).append(SPACE)
                            .append(qualifiers.get(j).getLinguisticVariableName());
                    summaries.add(new Summary(form, stringBuilder.toString(), getQualityForSummary()));
                }
            }
        }
        //Q1 P1 have Q# and Q# and Q# ...
        for (int i = 2; i < qualifiers.size(); i++) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(quantifier.getLabelName()).append(SPACE)
                    .append(subject).append(HAVE);
            for (int j = 0; j < i + 1; j++) {
                stringBuilder.append(qualifiers.get(i).getLabelName()).append(SPACE)
                        .append(qualifiers.get(i).getLinguisticVariableName());
                if (j + 1 < i + 1) {
                    stringBuilder.append(AND);
                }
            }
            summaries.add(new Summary(form, stringBuilder.toString(), getQualityForSummary()));
        }
    }

    private void generateSecondForm() {
        final int form = 2;
    }

    public List<Label<R>> getQualifiers() {
        return qualifiers;
    }

    public List<R> getPolicies() {
        return policies;
    }

    private List<Double> getQualityForSummary() {
        return Collections.emptyList();
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
