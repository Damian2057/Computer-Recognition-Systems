package p.lodz.pl.backend.fuzzy.summary;

import p.lodz.pl.backend.fuzzy.linguistic.Label;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;

import java.util.List;

public class LinguisticSummary<R> extends AbstractSummary<R> {

    private final Label<R> qualifier;
    private final List<Label<R>> summarizers;
    private final String subject;
    private final List<R> policies;

    public LinguisticSummary(Quantifier<R> quantifier,
                             Label<R> qualifier,
                             List<Label<R>> summarizers,
                             String subject, List<R> policies) {
        super(quantifier);
        this.qualifier = qualifier;
        this.summarizers = summarizers;
        this.subject = subject;
        this.policies = policies;
    }

    public Label<R> getQualifier() {
        return qualifier;
    }

    public List<Label<R>> getSummarizers() {
        return summarizers;
    }

    public List<R> getPolicies() {
        return policies;
    }

    @Override
    public String generateSummary() {
        return null;
    }
}
