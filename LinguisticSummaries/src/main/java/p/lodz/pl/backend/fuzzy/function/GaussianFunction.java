package p.lodz.pl.backend.fuzzy.function;

import p.lodz.pl.backend.fuzzy.function.domain.Domain;

public class GaussianFunction extends BasicFunction implements MembershipFunction {

    private final double middle;
    private final double width;

    public GaussianFunction(Domain domain, double middle, double width) {
        super(domain);
        this.middle = middle;
        this.width = width;
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
