package p.lodz.pl.logic.start;

import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;
import p.lodz.pl.dao.SerializeLoaderImpl;
import p.lodz.pl.dao.SerializeLoader;
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

    private static final Properties prop = Config.getProperties();
    private final SerializeLoader<List<Vector>> reader = new SerializeLoaderImpl();
    private final Classifier classifier;
    private final ConfusionMatrix confusionMatrix;

    public StarterImpl() {
        List<Vector> vectors;
        if (!reader.isFileExist()) {
            Extractor extractor = new FeatureExtractor();
            vectors = extractor.extract();
            reader.write(vectors);
        } else {
            SerializeLoader<List<Vector>> reader = new SerializeLoaderImpl();
            vectors = reader.read();
        }
        this.classifier = new KNNClassifier(vectors);
        this.confusionMatrix = new ConfusionMatrixImpl();
    }

    @Override
    public void start() {
        List<Vector> vectors = classifier.classifyTestSet();
        List<Category> qualityList = confusionMatrix.measureClassificationQuality(vectors);
        displayInfo(qualityList);
    }

    private void displayInfo(List<Category> categories) {

    }
}
