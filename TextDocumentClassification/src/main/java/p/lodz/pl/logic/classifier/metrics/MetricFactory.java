package p.lodz.pl.logic.classifier.metrics;

public class MetricFactory {

    public Metric getMetric(String metric) {
        switch (metric) {
            case "streetMetric" -> {
                return new StreetMetric();
            }
            case "euclideanMetric" -> {
                return new EuclideanMetric();
            }
            case "chebyshevMetric" -> {
                return new ChebyshevMetric();
            }
            default -> throw new RuntimeException("invalid metric type");
        }
    }
}
