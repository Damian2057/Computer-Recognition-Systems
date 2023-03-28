package p.lodz.pl.logic.classifier.metrics;

import p.lodz.pl.logic.classifier.Neighbor;
import p.lodz.pl.model.Vector;

public class EuclideanMetric extends SpecificMetric implements Metric {
    @Override
    public Neighbor calculateMetric(Vector vector, Vector neighbor) {
        double distance = 0.0;
        for (int i = 0; i < vector.getFeatures().size(); i++) {
            if (vector.getFeatures().get(i).getFeature() instanceof Double) {
                distance += Math.pow(vector.getFeatures().get(i).getAsDouble() -
                        neighbor.getFeatures().get(i).getAsDouble(), 2);
            } else {
                distance += Math.pow(measure.getDistanceBetweenTwoWords(vector.getFeatures().get(i).getAsString(),
                        neighbor.getFeatures().get(i).getAsString()), 2);
            }
        }
        distance = Math.sqrt(distance);
        return new Neighbor(neighbor.getArticleRealCountry(), distance);
    }
}
