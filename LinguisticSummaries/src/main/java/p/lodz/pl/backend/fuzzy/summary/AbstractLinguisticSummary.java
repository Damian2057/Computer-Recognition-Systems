package p.lodz.pl.backend.fuzzy.summary;

import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;

import java.util.List;

public abstract class AbstractLinguisticSummary {

    protected final Quantifier quantifier;

    protected AbstractLinguisticSummary(Quantifier quantifier) {
        this.quantifier = quantifier;
    }

    abstract List<Summary> generateSummary();

    public Quantifier getQuantifier() {
        return quantifier;
    }
}