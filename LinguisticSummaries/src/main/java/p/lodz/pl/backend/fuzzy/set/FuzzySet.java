package p.lodz.pl.backend.fuzzy.set;

import p.lodz.pl.backend.fuzzy.function.MembershipFunction;
import p.lodz.pl.backend.fuzzy.function.domain.Domain;
import p.lodz.pl.backend.fuzzy.util.Extractor;

import java.util.List;

public class FuzzySet<R> extends CrispSet {

    private final Extractor<R> extractor;

    public FuzzySet(Extractor<R> extractor, MembershipFunction function) {
        super(function);
        this.extractor = extractor;
    }

    public List<R> support(List<R> list) {
        return alphaCut(list, 0.0);
    }

    public List<R> alphaCut(List<R> list, double a) {
        return list.stream().filter(x -> function.getMemberShip(extractor.apply(x)) > a).toList();
    }

    public double degreeOfFuzziness(List<R> list) {
        return ((double) support(list).size()) / ((double) list.size());
    }

    public Domain getDomain() {
        return function.getDomain();
    }

    public boolean isEmpty(List<R> list) {
        return support(list).isEmpty();
    }

    public boolean isNormal(List<R> list) {
        return list.stream().anyMatch(x -> function.getMemberShip(extractor.apply(x)) == 1);
    }

    public double heightOfSet(List<R> list) {
        return list.stream()
                .mapToDouble(x -> function.getMemberShip(extractor.apply(x))).max()
                .orElse(0.0);
    }

    public boolean isConvex(List<R> list) {
        //TODO: complete
        return false;
    }

    public Extractor<R> getExtractor() {
        return extractor;
    }
}
