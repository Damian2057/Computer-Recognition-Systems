package p.lodz.pl.backend.fuzzy.summary;

import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;

public abstract class AbstractSummary<R> {

    protected final Quantifier quantifier;

    protected AbstractSummary(Quantifier quantifier) {
        this.quantifier = quantifier;
    }

    abstract String generateSummary();

    //TODO: complete
    protected double degreeOfTruth() {
        return 0.0;
    }

    protected double degreeOfImprecision() {
        return 0.0;
    }

    protected double degreeOfCovering() {
        return 0.0;
    }

    protected double degreeOfAppropriateness() {
        return 0.0;
    }

    protected double lengthOfSummary() {
        return 0.0;
    }

    protected double degreeOfQuantifierImprecision() {
        return 0.0;
    }

    protected double degreeOfQuantifierCardinality() {
        return 0.0;
    }

    protected double degreeOfSummarizerCardinality() {
        return 0.0;
    }

    protected double degreeOfQualifierImprecision() {
        return 0.0;
    }

    protected double degreeOfQualifierCardinality() {
        return 0.0;
    }

    public double lengthOfQualifier() {
        return 0.0;
    }

    public double quality() {
        return 0.0;
    }

    public Quantifier getQuantifier() {
        return quantifier;
    }
}
