package p.lodz.pl.backend.fuzzy.function;

import p.lodz.pl.backend.fuzzy.function.domain.Domain;

public class TrapezoidalFunction extends BasicFunction implements MembershipFunction {

    private final double a;
    private final double b;
    private final double c;
    private final double d;

    public TrapezoidalFunction(Domain domain, double a, double b, double c, double d) {
        super(domain);
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    @Override
    public double getMemberShip(Double x) {
        return domain.isInDomain(x) ? getValue(x) : 0.0;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    @Override
    public double cardinality() {
        return 0.5 * ((d - a) + (c - b)) * 1.0;
    }

    @Override
    public double support() {
        return d - a;
    }

    private double getValue(double x) {
        if (x > a && x < b) {
            return (x - a) / (b - a);
        } else if (x >= b && x <= c) {
            return 1.0;
        } else if (x > c && x < d) {
            return 1 - (x - c) / (d - c);
        } else {
            return 0.0;
        }
    }
}
