package p.lodz.pl.backend.fuzzy.summary;

import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;

import java.util.List;

public abstract class AbstractSummary<R> {

    protected final Quantifier quantifier;

    protected AbstractSummary(Quantifier quantifier) {
        this.quantifier = quantifier;
    }

    abstract List<Summary> generateSummary();

    public Quantifier getQuantifier() {
        return quantifier;
    }
}
