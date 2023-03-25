package p.lodz.pl.utility;

import lombok.extern.java.Log;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import p.lodz.pl.logic.extractor.Extractor;
import p.lodz.pl.logic.extractor.FeatureExtractor;
import p.lodz.pl.model.Vector;

import java.util.List;

@Log
public class CollectData {

    private static final boolean enable = true;
    private List<Vector> vectors;

    @BeforeClass
    public void setUp() {
        Extractor extractor = new FeatureExtractor();
        this.vectors =  extractor.extract();
    }

    @Test(enabled = enable, priority = 1)
    public void getVectorFeatureRanges() {
        getMinMax(vectors);

    }

    private void getMinMax(List<Vector> vectors) {
        int numberOfFeatures = vectors.get(0).getFeatures().size();
        for (int i = 0; i < numberOfFeatures; i++) {
            if (vectors.get(i).getFeatures().get(i).getFeature() instanceof Double) {
                double max = Double.MIN_VALUE;
                double min = Double.MAX_VALUE;
                for (Vector vector : vectors) {
                    double value = (double) vector.getFeatures().get(i).getFeature();
                    max = Math.max(value, max);
                    min = Math.min(value, min);
                }
                log.info(String.format("%s, min= %s, max= %s",
                        vectors.get(i).getFeatures().get(i).getType(),
                        min,
                        max));
            }
        }
    }
}
