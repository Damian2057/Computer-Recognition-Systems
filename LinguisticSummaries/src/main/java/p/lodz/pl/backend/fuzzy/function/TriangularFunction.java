package p.lodz.pl.backend.fuzzy.function;

import p.lodz.pl.backend.fuzzy.function.domain.Domain;

public class TriangularFunction extends BasicFunction implements MembershipFunction {

    private final double a;
    private final double b;
    private final double c;

    protected TriangularFunction(Domain domain, double a, double b, double c) {
        super(domain);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public double getMemberShip(Double x) {
        return domain.isInDomain(x) ? getValue(x) : 0.0;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    private double getValue(double x) {
        //TODO: COMPLETE
        return 0.0;
    }
}
