package p.lodz.pl.backend.fuzzy.function;

import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import p.lodz.pl.backend.fuzzy.function.domain.ContinuousDomain;
import p.lodz.pl.backend.fuzzy.function.domain.DiscreteDomain;
import p.lodz.pl.backend.fuzzy.function.domain.Domain;

import java.util.function.Function;

public class FunctionWrapper extends BasicFunction implements MembershipFunction{

    private final Function<Double, Double> function;

    public FunctionWrapper(Domain domain, Function<Double, Double> function) {
        super(domain);
        this.function = function;
    }

    @Override
    public double getMemberShip(Double x) {
        return function.apply(x);
    }

    @Override
    public Domain getDomain() {
        return domain;
    }

    @Override
    public double integral() {
        SimpsonIntegrator simpsonIntegrator = new SimpsonIntegrator();
        if (getDomain() instanceof ContinuousDomain continuousDomain) {
            return simpsonIntegrator.integrate(Integer.MAX_VALUE,
                    function::apply, continuousDomain.getMinDomain(), continuousDomain.getMaxDomain());
        } else if (getDomain() instanceof DiscreteDomain discreteDomain) {
            return discreteDomain.getPoints().stream().mapToDouble(function::apply).sum();
        }
        return 0;
    }

    @Override
    public double width() {
        return domain.width();
    }
}
