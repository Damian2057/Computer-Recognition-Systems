package p.lodz.pl.backend.fuzzy.util;

import java.util.function.Function;

@FunctionalInterface
public interface Extractor<R> extends Function<R, Double> {
}
