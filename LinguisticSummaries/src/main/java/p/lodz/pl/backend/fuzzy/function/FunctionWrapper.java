package p.lodz.pl.backend.fuzzy.function;

import p.lodz.pl.backend.fuzzy.function.domain.Domain;

import java.util.function.Function;

public class FunctionWrapper extends BasicFunction implements MembershipFunction{

    private final Function<Double, Double> function;

    public FunctionWrapper(Domain domain, Function<Double, Double> function) {
        super(domain);
        this.function = function;
    }

    @Override
    public double getMemberShip(Double x) {
        return function.apply(x);
    }

    @Override
    public Domain getDomain() {
        return null;
    }
}
