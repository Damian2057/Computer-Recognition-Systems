package p.lodz.pl.logic.start;

import p.lodz.pl.logic.classifier.Classifier;
import p.lodz.pl.logic.classifier.KNNClassifier;
import p.lodz.pl.logic.extractor.Extractor;
import p.lodz.pl.logic.extractor.FeatureExtractor;
import p.lodz.pl.logic.quality.Category;
import p.lodz.pl.logic.quality.ConfusionMatrix;
import p.lodz.pl.logic.quality.ConfusionMatrixImpl;
import p.lodz.pl.model.Vector;

import java.util.List;

public class StarterImpl implements Starter {

    private final Classifier classifier;
    private final ConfusionMatrix confusionMatrix;

    public StarterImpl() {
        Extractor extractor = new FeatureExtractor();
        this.classifier = new KNNClassifier(extractor.extract());
        this.confusionMatrix = new ConfusionMatrixImpl();
    }

    @Override
    public void start() {
//        List<Vector> vectors = classifier.classifyTestSet();
//        List<Category> qualityList = confusionMatrix.measureClassificationQuality(vectors);
//        displayInfo(qualityList);
    }

    private void displayInfo(List<Category> categories) {

    }
}
