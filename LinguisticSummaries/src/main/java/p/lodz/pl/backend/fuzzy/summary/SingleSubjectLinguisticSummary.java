package p.lodz.pl.backend.fuzzy.summary;

import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;

import java.util.ArrayList;
import java.util.List;

public class SingleSubjectLinguisticSummary<R> extends AbstractLinguisticSummary {

    private final List<LinguisticLabel<R>> qualifiers;
    private final String subject;
    private final List<R> policies;

    public SingleSubjectLinguisticSummary(Quantifier quantifier,
                                          List<LinguisticLabel<R>> qualifiers,
                                          String subject, List<R> policies) {
        super(quantifier);
        this.qualifiers = qualifiers;
        this.subject = subject;
        this.policies = policies;
    }

    public List<LinguisticLabel<R>> getQualifiers() {
        return qualifiers;
    }

    public List<R> getPolicies() {
        return policies;
    }

    @Override
    public List<Summary> generateSummary() {
        List<Summary> summaries = new ArrayList<>();


        return summaries;
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
