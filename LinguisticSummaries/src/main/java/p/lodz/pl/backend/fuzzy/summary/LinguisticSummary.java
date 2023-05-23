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
    public String generateSummary() {
        return null;
    }
}
