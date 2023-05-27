package p.lodz.pl.backend.fuzzy.set;

import p.lodz.pl.backend.fuzzy.function.MembershipFunction;
import p.lodz.pl.backend.fuzzy.function.domain.ContinuousDomain;
import p.lodz.pl.backend.fuzzy.function.domain.DiscreteDomain;
import p.lodz.pl.backend.fuzzy.function.domain.Domain;
import p.lodz.pl.backend.fuzzy.function.domain.DomainWrapper;
import p.lodz.pl.backend.fuzzy.util.Extractor;
import p.lodz.pl.backend.fuzzy.util.Pair;

import java.util.List;
import java.util.stream.DoubleStream;

public class FuzzySet<R> extends CrispSet {

    private final Extractor<R> extractor;

    public FuzzySet(Extractor<R> extractor, MembershipFunction function) {
        super(function);
        this.extractor = extractor;
    }

    public List<R> support(List<R> list) {
        return alphaCut(list, 0.0);
    }

    public double support() {
        return function.support();
    }

    public double getMemberShip(R x) {
        return function.getMemberShip(extractor.apply(x));
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

    public boolean isNormal() {
        if (function.getDomain() instanceof ContinuousDomain continuousDomain) {
            double step = 0.01;
            for (double i = continuousDomain.getMinDomain(); i <= continuousDomain.getMaxDomain(); i+= step) {
                if (function.getMemberShip(i) == 1.0) {
                    return true;
                }
            }
            return false;
        } else if (function.getDomain() instanceof DiscreteDomain discreteDomain) {
            for (Double x: discreteDomain.getPoints()) {
                if (function.getMemberShip(x) == 1.0) {
                    return true;
                }
            }
        } else if (function.getDomain() instanceof DomainWrapper wrapper) {
            double step = 0.01;
            for (Pair<Double, Double> pair: wrapper.getDomains()) {
                for (double i = pair.getFirst(); i <= pair.getSecond(); i+= step) {
                    if (function.getMemberShip(i) == 1.0) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    public double heightOfSet() {
        double max = Double.MIN_VALUE;
        if (function.getDomain() instanceof ContinuousDomain continuousDomain) {
            double step = 0.01;
            for (double i = continuousDomain.getMinDomain(); i <= continuousDomain.getMaxDomain(); i+= step) {
                max = Math.max(max, function.getMemberShip(i));
            }
        } else if (function.getDomain() instanceof DiscreteDomain discreteDomain) {
            for (Double x: discreteDomain.getPoints()) {
                max = Math.max(max, function.getMemberShip(x));
            }
        } else if (function.getDomain() instanceof DomainWrapper wrapper) {
            double step = 0.01;
            for (Pair<Double, Double> pair: wrapper.getDomains()) {
                for (double i = pair.getFirst(); i <= pair.getSecond(); i+= step) {
                    max = Math.max(max, function.getMemberShip(i));
                }
            }
        }
        return max;
    }

    public boolean isConvex(List<R> list) {
        //TODO: complete
        return false;
    }

    public double cardinality(List<R> list) {
        return list.stream().mapToDouble(x -> function.getMemberShip(extractor.apply(x))).sum();
    }

    public double degreeOfFuzziness() {
        return cardinality() / support();
    }

    public double cardinality() {
        return function.cardinality();
    }

    public Extractor<R> getExtractor() {
        return extractor;
    }
}
