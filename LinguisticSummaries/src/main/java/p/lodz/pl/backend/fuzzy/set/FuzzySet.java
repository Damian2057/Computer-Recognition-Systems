package p.lodz.pl.backend.fuzzy.set;

import p.lodz.pl.backend.fuzzy.function.MembershipFunction;
import p.lodz.pl.backend.fuzzy.function.domain.ContinuousDomain;
import p.lodz.pl.backend.fuzzy.function.domain.DiscreteDomain;
import p.lodz.pl.backend.fuzzy.function.domain.Domain;
import p.lodz.pl.backend.fuzzy.function.domain.DomainWrapper;
import p.lodz.pl.backend.fuzzy.util.Extractor;
import p.lodz.pl.backend.fuzzy.util.Pair;

import java.util.List;

public class FuzzySet<R> extends CrispSet<R> {


    public FuzzySet(Extractor<R> extractor, MembershipFunction function) {
        super(function, extractor);
    }

    public double support() {
        return function.width();
    }

    public double getMemberShip(R x) {
        return function.getMemberShip(extractor.apply(x));
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

    public boolean isConvex() {
        if (function.getDomain() instanceof ContinuousDomain continuousDomain) {
            double step = 0.001;
            for (double i = continuousDomain.getMinDomain(); i < continuousDomain.getMaxDomain(); i += step) {
                double x = function.getMemberShip(i);
                double y = function.getMemberShip(i + step);
                double z = function.getMemberShip(i + 2 * step);
                if (x > y && y > z) {
                    return false;
                }
            }
            return true;
        } else if (function.getDomain() instanceof DiscreteDomain discreteDomain) {
            for (int i = 0; i < discreteDomain.getPoints().size() - 2; i++) {
                double x = function.getMemberShip(discreteDomain.getPoints().get(i));
                double y = function.getMemberShip(discreteDomain.getPoints().get(i + 1));
                double z = function.getMemberShip(discreteDomain.getPoints().get(i + 2));
                if (x > y && y > z) {
                    return false;
                }
            }
            return true;
        } else if (function.getDomain() instanceof DomainWrapper wrapper) {
            double step = 0.001;
            for (Pair<Double, Double> pair: wrapper.getDomains()) {
                for (double i = pair.getFirst(); i < pair.getSecond(); i += step) {
                    double x = function.getMemberShip(i);
                    double y = function.getMemberShip(i + step);
                    double z = function.getMemberShip(i + 2 * step);
                    if (x > y && y > z) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    public double degreeOfFuzziness() {
        return cardinality() / getDomain().width();
    }

    public double cardinality() {
        return function.integral();
    }

    public double supportCardinality() {
        return function.width() / getDomain().width();
    }

    public Extractor<R> getExtractor() {
        return extractor;
    }
}
