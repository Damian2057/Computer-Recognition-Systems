package p.lodz.pl.backend.fuzzy.set;

import p.lodz.pl.backend.fuzzy.function.MembershipFunction;
import p.lodz.pl.backend.fuzzy.util.Extractor;

import java.util.List;

public class CrispSet<R> {

    protected final MembershipFunction function;
    protected final Extractor<R> extractor;

    public CrispSet(MembershipFunction function, Extractor<R> extractor) {
        this.function = function;
        this.extractor = extractor;
    }

    public double getMemberShip(double x) {
        return function.getMemberShip(x);
    }

    public List<R> support(List<R> list) {
        return alphaCut(list, 0.0);
    }

    public List<R> alphaCut(List<R> list, double a) {
        return list.stream().filter(x -> function.getMemberShip(extractor.apply(x)) > a).toList();
    }

    public Extractor<R> getExtractor() {
        return extractor;
    }

    public MembershipFunction getFunction() {
        return function;
    }
}
