package p.lodz.pl.backend.fuzzy.function;

import p.lodz.pl.backend.fuzzy.function.domain.Domain;

public abstract class BasicFunction {
    protected final Domain domain;

    protected BasicFunction(Domain domain) {
        this.domain = domain;
    }
}
