package p.lodz.pl.backend.fuzzy.function;

import p.lodz.pl.backend.fuzzy.function.domain.Domain;

import java.util.function.Function;

public class GaussianFunction extends BasicFunction implements MembershipFunction {

    private final double middle;
    private final double width;
    private final Function<Double, Double> function;

    public GaussianFunction(Domain domain, double middle, double width) {
        super(domain);
        this.middle = middle;
        this.width = width;
        this.function = x -> Math.exp(-(middle - x) * (width - x) / (2 * width * width));
    }

    @Override
    public double getMemberShip(Double x) {
        return domain.isInDomain(x) ? Math.exp(-(middle - x) * (width - x) / (2 * width * width)) : 0.0;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    public double getMiddle() {
        return middle;
    }

    public double getWidth() {
        return width;
    }
}
