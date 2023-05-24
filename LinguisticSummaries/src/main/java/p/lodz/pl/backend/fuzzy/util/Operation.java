package p.lodz.pl.backend.fuzzy.util;

import p.lodz.pl.backend.fuzzy.function.FunctionWrapper;
import p.lodz.pl.backend.fuzzy.function.MembershipFunction;
import p.lodz.pl.backend.fuzzy.set.CrispSet;
import p.lodz.pl.backend.fuzzy.set.FuzzySet;

public class Operation<R extends CrispSet> {

    /**
     * Get Sum of two FuzzySet
     */
    public R and(R first, R second) {
        MembershipFunction function = new FunctionWrapper(first.getFunction().getDomain(), x -> Math.min(first.getMemberShip(x), second.getMemberShip(x)));
        if (first instanceof FuzzySet fuzzySet) {
            return ((R) new FuzzySet<>(fuzzySet.getExtractor(), function));
        } else {
            return ((R) new CrispSet(function));
        }
    }

    /**
     * Get multiplication of two FuzzySet
     */
    public R or(R first, R second) {
        MembershipFunction function = new FunctionWrapper(first.getFunction().getDomain(), x -> Math.max(first.getMemberShip(x), second.getMemberShip(x)));
        if (first instanceof FuzzySet fuzzySet) {
            return ((R) new FuzzySet<>(fuzzySet.getExtractor(), function));
        } else {
            return ((R) new CrispSet(function));
        }
    }

    /**
     * Get complement of FuzzySet
     */
    public R not(R first) {
        MembershipFunction function = new FunctionWrapper(first.getFunction().getDomain(), x -> 1 - first.getMemberShip(x));
        if (first instanceof FuzzySet fuzzySet) {
            return ((R) new FuzzySet<>(fuzzySet.getExtractor(), function));
        } else {
            return ((R) new CrispSet(function));
        }
    }
}
