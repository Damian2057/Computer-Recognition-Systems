package p.lodz.pl.backend.fuzzy.quantifier;

import p.lodz.pl.backend.fuzzy.function.MembershipFunction;
import p.lodz.pl.backend.fuzzy.linguistic.Label;
import p.lodz.pl.backend.fuzzy.util.Extractor;

public class Quantifier extends Label<Double> {

    private final boolean isAbsolute;

    public Quantifier(String name, Extractor<Double> extractor, MembershipFunction function, boolean isAbsolute) {
        super("", name, extractor, function);
        this.isAbsolute = isAbsolute;
    }

    public boolean isAbsolute() {
        return isAbsolute;
    }

    @Override
    public String getLinguisticVariableName() {
        throw new UnsupportedOperationException("Quantifier does not have linguistic variable name");
    }
}
