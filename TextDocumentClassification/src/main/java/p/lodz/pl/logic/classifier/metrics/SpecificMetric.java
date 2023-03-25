package p.lodz.pl.logic.classifier.metrics;

import p.lodz.pl.config.Config;
import p.lodz.pl.logic.classifier.measures.Measure;
import p.lodz.pl.logic.classifier.measures.MeasureFactory;

public abstract class SpecificMetric implements Metric {

    protected final Measure measure;

    public SpecificMetric() {
        MeasureFactory factory = new MeasureFactory();
        this.measure = factory.getMeasure(Config.getProperties().getMeasure());
    }
}
