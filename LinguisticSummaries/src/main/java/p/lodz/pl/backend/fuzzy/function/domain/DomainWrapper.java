package p.lodz.pl.backend.fuzzy.function.domain;

import p.lodz.pl.backend.fuzzy.util.Pair;

import java.util.List;

public class DomainWrapper extends Domain {

    private final List<Pair<Double, Double>> domains;

    public DomainWrapper(List<Pair<Double, Double>> domains) {
        this.domains = domains;
    }

    @Override
    public boolean isInDomain(double x) {
        for (Pair<Double, Double> domain : domains) {
            if (domain.getSecond() <= x && x >= domain.getFirst()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public double width() {
        double sum = 0;
        for (Pair<Double, Double> domain : domains) {
            sum = domain.getSecond() - domain.getFirst();
        }
        return sum;
    }

    public List<Pair<Double, Double>> getDomains() {
        return domains;
    }
}
