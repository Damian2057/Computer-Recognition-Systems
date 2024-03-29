package p.lodz.pl.backend.fuzzy.util;

import p.lodz.pl.backend.fuzzy.function.FunctionWrapper;
import p.lodz.pl.backend.fuzzy.function.MembershipFunction;
import p.lodz.pl.backend.fuzzy.function.domain.ContinuousDomain;
import p.lodz.pl.backend.fuzzy.function.domain.DiscreteDomain;
import p.lodz.pl.backend.fuzzy.function.domain.Domain;
import p.lodz.pl.backend.fuzzy.function.domain.DomainWrapper;
import p.lodz.pl.backend.fuzzy.set.CrispSet;
import p.lodz.pl.backend.fuzzy.set.FuzzySet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Operation<R extends CrispSet> {

    /**
     * Get Sum of two FuzzySet
     */
    public R and(R first, R second) {
        MembershipFunction function = new FunctionWrapper(connectDomains(first.getFunction().getDomain(),
                second.getFunction().getDomain()),
                x -> Math.min(first.getMemberShip(x), second.getMemberShip(x)));
        if (first instanceof FuzzySet fuzzySet) {
            return ((R) new FuzzySet<>(fuzzySet.getExtractor(), function));
        } else {
            return ((R) new CrispSet(function, first.getExtractor()));
        }
    }

    /**
     * Get multiplication of two FuzzySet
     */
    public R or(R first, R second) {
        MembershipFunction function = new FunctionWrapper(connectDomains(first.getFunction().getDomain(),
                second.getFunction().getDomain()),
                x -> Math.max(first.getMemberShip(x), second.getMemberShip(x)));
        if (first instanceof FuzzySet fuzzySet) {
            return ((R) new FuzzySet<>(fuzzySet.getExtractor(), function));
        } else {
            return ((R) new CrispSet(function, first.getExtractor()));
        }
    }

    /**
     * Get complement of FuzzySet
     */
    public R not(R first) {
        MembershipFunction function = new FunctionWrapper(first.getFunction().getDomain(),
                x -> 1 - first.getMemberShip(x));
        if (first instanceof FuzzySet fuzzySet) {
            return ((R) new FuzzySet<>(fuzzySet.getExtractor(), function));
        } else {
            return ((R) new CrispSet(function, first.getExtractor()));
        }
    }

    private Domain connectDomains(Domain first, Domain second) {
        if (first instanceof ContinuousDomain continuousDomain && second instanceof ContinuousDomain continuousDomain1) {
            if (continuousDomain.getMinDomain() >= continuousDomain1.getMinDomain()
                    && continuousDomain.getMaxDomain() >= continuousDomain1.getMaxDomain()) {
                return first;
            } else if (continuousDomain1.getMinDomain() >= continuousDomain.getMinDomain()
                    && continuousDomain1.getMaxDomain() >= continuousDomain.getMaxDomain()) {
                return second;
            } else if (continuousDomain.getMinDomain() >= continuousDomain1.getMinDomain()
                    && continuousDomain.getMaxDomain() <= continuousDomain1.getMaxDomain()) {
                return new ContinuousDomain(continuousDomain.getMinDomain(), continuousDomain1.getMaxDomain());
            } else if (continuousDomain.getMinDomain() <= continuousDomain1.getMinDomain()
                    && continuousDomain.getMaxDomain() >= continuousDomain1.getMaxDomain()) {
                return new ContinuousDomain(continuousDomain1.getMinDomain(), continuousDomain.getMaxDomain());
            } else {
                List<Pair<Double, Double>> pairs = new ArrayList<>();
                pairs.add(new Pair<>(continuousDomain.getMinDomain(), continuousDomain.getMaxDomain()));
                pairs.add(new Pair<>(continuousDomain1.getMinDomain(), continuousDomain1.getMaxDomain()));
                return new DomainWrapper(pairs);
            }
        } else if (first instanceof DiscreteDomain discreteDomain && second instanceof DiscreteDomain discreteDomain1) {
            Set<Double> dom = new HashSet<>(discreteDomain.getPoints());
            dom.addAll(discreteDomain1.getPoints());
            discreteDomain.setPoints(dom.stream().toList());
            return discreteDomain;
        } else {
            double min = 0.0;
            double max = 0.0;
            if (first instanceof ContinuousDomain continuousDomain) {
                min = continuousDomain.getMinDomain();
                max = continuousDomain.getMaxDomain();
            } else if (first instanceof DiscreteDomain discreteDomain) {
                min = discreteDomain.getPoints().stream().min(Double::compareTo).orElseThrow();
                max = discreteDomain.getPoints().stream().max(Double::compareTo).orElseThrow();
            }
            if (second instanceof ContinuousDomain continuousDomain) {
                min = Math.min(min, continuousDomain.getMinDomain());
                max = Math.max(max, continuousDomain.getMaxDomain());
            } else if (second instanceof DiscreteDomain discreteDomain) {
                min = Math.min(min, discreteDomain.getPoints().stream().min(Double::compareTo).orElseThrow());
                max = Math.max(max, discreteDomain.getPoints().stream().max(Double::compareTo).orElseThrow());
            }
            return new ContinuousDomain(min, max);
        }
    }
}
