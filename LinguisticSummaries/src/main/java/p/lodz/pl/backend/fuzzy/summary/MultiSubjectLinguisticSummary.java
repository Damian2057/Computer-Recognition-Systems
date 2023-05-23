package p.lodz.pl.backend.fuzzy.summary;

import p.lodz.pl.backend.fuzzy.linguistic.Label;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;

import java.util.List;

public class MultiSubjectLinguisticSummary<R> extends AbstractSummary<R> {

    private final List<Label<R>> qualifiers;
    private final String subject1;
    private final String subject2;
    private final List<R> policies;

    protected MultiSubjectLinguisticSummary(Quantifier<R> quantifier,
                                            List<Label<R>> qualifiers,
                                            String subject1, String subject2, List<R> policies) {
        super(quantifier);
        this.qualifiers = qualifiers;
        this.subject1 = subject1;
        this.subject2 = subject2;
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
}
