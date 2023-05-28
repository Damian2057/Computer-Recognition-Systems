package p.lodz.pl.backend.fuzzy.function;

import org.apache.commons.math3.analysis.integration.SimpsonIntegrator;
import p.lodz.pl.backend.fuzzy.function.domain.ContinuousDomain;
import p.lodz.pl.backend.fuzzy.function.domain.DiscreteDomain;
import p.lodz.pl.backend.fuzzy.function.domain.Domain;

import java.util.function.Function;

public class GaussianFunction extends BasicFunction implements MembershipFunction {

    private final double middle;
    private final double width;
    private final Function<Double, Double> function;

    public GaussianFunction(Domain domain, double middle, double width) {
        super(domain);
        this.middle = middle;
        this.width = width;
        this.function = x -> Math.exp(-(middle - x) * (width - x) / (2 * width * width));
    }

    @Override
    public double getMemberShip(Double x) {
        return domain.isInDomain(x) ? function.apply(x) : 0.0;
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
            return discreteDomain.getPoints().stream().mapToDouble(function::apply).sum()
                    / discreteDomain.getPoints().size();
        }
        return 0;
    }

    @Override
    public double width() {
        return domain.width();
    }

    public double getMiddle() {
        return middle;
    }

    public double getWidth() {
        return width;
    }
}
