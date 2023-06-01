package p.lodz.pl.backend.fuzzy.set;

import p.lodz.pl.backend.fuzzy.function.MembershipFunction;
import p.lodz.pl.backend.fuzzy.util.Extractor;

public class CrispSet<R> {

    protected final MembershipFunction function;
    protected final Extractor<R> extractor;

    public CrispSet(MembershipFunction function, Extractor<R> extractor) {
        this.function = function;
        this.extractor = extractor;
    }

    public double getMemberShip(double x) {
        return function.getMemberShip(x);
    }

    public Extractor<R> getExtractor() {
        return extractor;
    }

    public MembershipFunction getFunction() {
        return function;
    }
}
