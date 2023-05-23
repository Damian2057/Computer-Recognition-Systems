package p.lodz.pl.backend.fuzzy.summary;

import p.lodz.pl.backend.fuzzy.linguistic.Label;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;

import java.util.List;

public class LinguisticSummary<R> extends AbstractSummary<R> {

    private final List<Label<R>> qualifiers;
    private final String subject;
    private final List<R> policies;

    public LinguisticSummary(Quantifier<R> quantifier,
                             List<Label<R>> qualifiers,
                             String subject, List<R> policies) {
        super(quantifier);
        this.qualifiers = qualifiers;
        this.subject = subject;
        this.policies = policies;
    }

    public List<Label<R>> getQualifiers() {
        return qualifiers;
    }

    public List<R> getPolicies() {
        return policies;
    }

    @Override
    public List<Summary> generateSummary() {
        return null;
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
        return 0.0;
    }
}
