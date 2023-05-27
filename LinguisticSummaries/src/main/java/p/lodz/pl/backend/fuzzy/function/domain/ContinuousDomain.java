package p.lodz.pl.backend.fuzzy.function.domain;

public class ContinuousDomain extends Domain {
    private final double minDomain;
    private final double maxDomain;

    public ContinuousDomain(double minDomain, double maxDomain) {
        this.minDomain = minDomain;
        this.maxDomain = maxDomain;
    }

    @Override
    public boolean isInDomain(double x) {
        return x >= minDomain && x <= maxDomain;
    }

    @Override
    public double width() {
        return maxDomain - minDomain;
    }

    public double getMinDomain() {
        return minDomain;
    }

    public double getMaxDomain() {
        return maxDomain;
    }
}
