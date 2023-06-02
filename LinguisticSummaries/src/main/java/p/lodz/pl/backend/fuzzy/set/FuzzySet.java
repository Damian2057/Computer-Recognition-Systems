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

    public double getMemberShip(R x) {
        return function.getMemberShip(extractor.apply(x));
    }

    public Domain getDomain() {
        return function.getDomain();
    }

    public <T> T getDomainCast() {
        return (T) function.getDomain();
    }

    public boolean isEmpty(List<R> list) {
        return support(list).isEmpty();
    }

    public boolean isNormal() {
        if (function.getDomain() instanceof ContinuousDomain continuousDomain) {
            double step = 0.01;
            for (double i = continuousDomain.getMinDomain(); i <= continuousDomain.getMaxDomain(); i = Math.round((i + step) * 100.0) / 100.0) {
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
                for (double i = pair.getFirst(); i <= pair.getSecond(); i = Math.round((i + step) * 100.0) / 100.0) {
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
            for (double i = continuousDomain.getMinDomain(); i <= continuousDomain.getMaxDomain(); i = Math.round((i + step) * 100.0) / 100.0) {
                max = Math.max(max, function.getMemberShip(i));
            }
        } else if (function.getDomain() instanceof DiscreteDomain discreteDomain) {
            for (Double x: discreteDomain.getPoints()) {
                max = Math.max(max, function.getMemberShip(x));
            }
        } else if (function.getDomain() instanceof DomainWrapper wrapper) {
            double step = 0.01;
            for (Pair<Double, Double> pair: wrapper.getDomains()) {
                for (double i = pair.getFirst(); i <= pair.getSecond(); i = Math.round((i + step) * 100.0) / 100.0) {
                    max = Math.max(max, function.getMemberShip(i));
                }
            }
        }
        return max;
    }

    public boolean isConvex() {
        if (function.getDomain() instanceof ContinuousDomain continuousDomain) {
            double step = 0.01;
            for (double i = continuousDomain.getMinDomain(); i <= continuousDomain.getMaxDomain(); i = Math.round((i + step) * 100.0) / 100.0) {
                for (double j = i; j <= continuousDomain.getMaxDomain(); j = Math.round((j + step) * 100.0) / 100.0) {
                    for (double k = j; k <= continuousDomain.getMaxDomain(); k = Math.round((k + step) * 100.0) / 100.0) {
                        if (i > j && j > k) {
                            return false;
                        }
                    }
                }
            }
            return true;
        } else if (function.getDomain() instanceof DiscreteDomain discreteDomain) {
            for (int i = 0; i < discreteDomain.getPoints().size(); i++) {
                for (int j = i; j < discreteDomain.getPoints().size(); j++) {
                    for (int k = j; k < discreteDomain.getPoints().size(); k++) {
                        if (i > j && j > k) {
                            return false;
                        }
                    }
                }
            }
            return true;
        } else if (function.getDomain() instanceof DomainWrapper wrapper) {
            double step = 0.01;
            for (Pair<Double, Double> pair: wrapper.getDomains()) {
                for (double i = pair.getFirst(); i <= pair.getSecond(); i = Math.round((i + step) * 100.0) / 100.0) {
                    for (double j = i; j <= pair.getSecond(); j = Math.round((j + step) * 100.0) / 100.0) {
                        for (double k = j; k <= pair.getSecond(); k = Math.round((k + step) * 100.0) / 100.0) {
                            if (i > j && j > k) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public List<R> support(List<R> list) {
        return alphaCut(list, 0.0);
    }

    public double support() {
        return function.width();
    }

    public List<R> alphaCut(List<R> list, double a) {
        return list.stream().filter(x -> function.getMemberShip(extractor.apply(x)) > a).toList();
    }

    public double degreeOfFuzziness(List<R> list) {
        return 1.0 * support(list).size() / list.size();
    }

    public double degreeOfFuzziness() {
        return support() / getDomain().width();
    }

    public double cardinality() {
        return function.integral();
    }

    public double cardinality(List<R> list) {
        return list.stream()
                .mapToDouble(x -> function.getMemberShip(extractor.apply(x)))
                .sum();
    }

    public Extractor<R> getExtractor() {
        return extractor;
    }
}
