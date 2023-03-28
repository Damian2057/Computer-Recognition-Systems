package p.lodz.pl.logic.classifier.metrics;

import p.lodz.pl.logic.classifier.Neighbor;
import p.lodz.pl.model.Vector;

public class ChebyshevMetric extends SpecificMetric implements Metric {
    @Override
    public Neighbor calculateMetric(Vector vector, Vector neighbor) {
        double maxDistance = Double.MIN_VALUE;
        for (int i = 0; i < vector.getFeatures().size(); i++) {
            double distance = 0;
            if (vector.getFeatures().get(i).getFeature() instanceof Double) {
                distance += Math.abs(vector.getFeatures().get(i).getAsDouble() -
                        neighbor.getFeatures().get(i).getAsDouble());
            } else {
                distance += Math.abs(measure.getDistanceBetweenTwoWords(vector.getFeatures().get(i).getAsString(),
                        neighbor.getFeatures().get(i).getAsString()));
            }
            maxDistance = Math.max(distance, maxDistance);
        }
        return new Neighbor(neighbor.getArticleRealCountry(), maxDistance);
    }
}
