package p.lodz.pl.backend.fuzzy.function;

import p.lodz.pl.backend.fuzzy.function.domain.Domain;

public class TriangularFunction extends TrapezoidalFunction {

    private final double a;
    private final double b;
    private final double c;

    public TriangularFunction(Domain domain, double a, double b, double c) {
        super(domain, a, b, b, c);
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public Domain getDomain() {
        return domain;
    }
}
