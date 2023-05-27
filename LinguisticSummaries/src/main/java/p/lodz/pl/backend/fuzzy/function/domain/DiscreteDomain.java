package p.lodz.pl.backend.fuzzy.function.domain;

import java.util.List;

public class DiscreteDomain extends Domain {
    private List<Double> points;

    public DiscreteDomain(List<Double> points) {
        this.points = points;
    }

    @Override
    public boolean isInDomain(double x) {
        return points.contains(x);
    }

    @Override
    public double width() {
        return points.size();
    }

    public List<Double> getPoints() {
        return points;
    }

    public void setPoints(List<Double> points) {
        this.points = points;
    }
}
