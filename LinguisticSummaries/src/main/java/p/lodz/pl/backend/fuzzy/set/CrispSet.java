package p.lodz.pl.backend.fuzzy.set;

import p.lodz.pl.backend.fuzzy.function.MembershipFunction;

public class CrispSet {

    protected final MembershipFunction function;

    protected CrispSet(MembershipFunction function) {
        this.function = function;
    }

    public double getMemberShip(double x) {
        return function.getMemberShip(x);
    }

    public MembershipFunction getFunction() {
        return function;
    }
}
