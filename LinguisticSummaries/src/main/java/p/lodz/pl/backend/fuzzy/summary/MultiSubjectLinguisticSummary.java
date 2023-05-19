package p.lodz.pl.backend.fuzzy.summary;

import p.lodz.pl.backend.fuzzy.linguistic.Label;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;

import java.util.List;

public class MultiSubjectLinguisticSummary<R> extends AbstractSummary<R> {

    private final Label<R> qualifier1;
    private final Label<R> qualifier2;
    private final List<Label<R>> summarizers;
    private final String subject1;
    private final String subject2;
    private final List<R> policies;

    protected MultiSubjectLinguisticSummary(Quantifier<R> quantifier,
                                            Label<R> qualifier1,
                                            Label<R> qualifier2,
                                            List<Label<R>> summarizers,
                                            String subject1, String subject2, List<R> policies) {
        super(quantifier);
        this.qualifier1 = qualifier1;
        this.qualifier2 = qualifier2;
        this.summarizers = summarizers;
        this.subject1 = subject1;
        this.subject2 = subject2;
        this.policies = policies;
    }

    @Override
    public String generateSummary() {
        return null;
    }

    public double degreeOfTruth() {
        return 0.0;
    }

    public Label<R> getQualifier1() {
        return qualifier1;
    }

    public Label<R> getQualifier2() {
        return qualifier2;
    }

    public List<Label<R>> getSummarizers() {
        return summarizers;
    }

    public List<R> getPolicies() {
        return policies;
    }
}
