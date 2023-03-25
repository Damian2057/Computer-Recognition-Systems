package p.lodz.pl.logic.classifier.measures;

public class MeasureFactory {

    public Measure getMeasure(String measure) {
        switch (measure) {
            case "ngrams" -> {
                return new NGramMeasure();
            }
            default -> throw new RuntimeException("invalid measure type");
        }
    }
}
