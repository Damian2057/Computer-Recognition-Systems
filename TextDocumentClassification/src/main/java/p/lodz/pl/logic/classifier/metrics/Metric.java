package p.lodz.pl.logic.classifier.metrics;

import p.lodz.pl.logic.classifier.Neighbor;
import p.lodz.pl.model.Vector;

public interface Metric {
    Neighbor calculateMetric(Vector vector, Vector neighbor);
}
