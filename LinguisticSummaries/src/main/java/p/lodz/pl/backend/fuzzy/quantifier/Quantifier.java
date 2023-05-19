package p.lodz.pl.backend.fuzzy.quantifier;

import p.lodz.pl.backend.fuzzy.function.MembershipFunction;
import p.lodz.pl.backend.fuzzy.linguistic.Label;
import p.lodz.pl.backend.fuzzy.util.Extractor;

public class Quantifier<R> extends Label<R> {

    private final boolean isAbsolute;

    public Quantifier(String labelName, Extractor<R> extractor, MembershipFunction function, boolean isAbsolute) {
        super(labelName, extractor, function);
        this.isAbsolute = isAbsolute;
    }


    public boolean isAbsolute() {
        return isAbsolute;
    }

    public double compatibilityLevel(double x) {
        return function.getMemberShip(x);
    }
}
