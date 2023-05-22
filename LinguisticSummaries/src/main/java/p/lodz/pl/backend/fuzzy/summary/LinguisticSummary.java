package p.lodz.pl.backend.fuzzy.summary;

import p.lodz.pl.backend.fuzzy.linguistic.Label;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;

import java.util.List;

public class LinguisticSummary<R> extends AbstractSummary<R> {

    private final List<Label<R>> qualifier;
    private final String subject;
    private final List<R> policies;

    public LinguisticSummary(Quantifier<R> quantifier,
                             List<Label<R>> qualifier,
                             String subject, List<R> policies) {
        super(quantifier);
        this.qualifier = qualifier;
        this.subject = subject;
        this.policies = policies;
    }

    public List<R> getPolicies() {
        return policies;
    }

    @Override
    public String generateSummary() {
        return null;
    }
}
