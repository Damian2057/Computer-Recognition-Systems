package p.lodz.pl.backend.fuzzy.function;

import p.lodz.pl.backend.fuzzy.function.domain.Domain;

public interface MembershipFunction {
    double getMemberShip(Double x);
    Domain getDomain();
    double cardinality();
    double support();
}
