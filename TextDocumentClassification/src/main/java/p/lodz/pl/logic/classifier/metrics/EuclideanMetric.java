package p.lodz.pl.logic.classifier.metrics;

import p.lodz.pl.logic.classifier.Neighbor;
import p.lodz.pl.model.Vector;

public class EuclideanMetric extends SpecificMetric implements Metric {
    @Override
    public Neighbor calculateMetric(Vector vector, Vector neighbor) {
        return new Neighbor("test", 5);
    }
}
