package p.lodz.pl.logic.start;

import lombok.extern.java.Log;
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

@Log
public class StarterImpl implements Starter {

    private static final Properties prop = Config.getProperties();
    private final Classifier classifier;
    private final ConfusionMatrix confusionMatrix;

    public StarterImpl() {
        List<Vector> vectors;
        SerializeLoader<List<Vector>> reader1 = new SerializeLoaderImpl();

        if (!reader1.isFileExist()) {
            Extractor extractor = new FeatureExtractor();
            vectors = extractor.extract();
            reader1.write(vectors);
        } else {
            SerializeLoader<List<Vector>> reader = new SerializeLoaderImpl();
            vectors = reader.read();
            log.info(vectors.size() + " vectors loaded");
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
        String county = "usa\t\tuk\t\tcanada\tfrance\tjapan\twest-germany\n";
        StringBuilder builder = new StringBuilder();
        for (Category category : categories) {
            builder.append("===========================\n")
                    .append(category.getType())
                    .append("\n===========================\n")
                    .append(String.format("| ACC | %s\n", category.getAcc()))
                    .append(String.format("| PRE | %s\n", category.getPre()))
                    .append(String.format("| REC | %s\n", category.getRec()))
                    .append(String.format("| F1  | %s\n", category.getF1()));
        }
        log.info("\n" + builder);

        StringBuilder matrixBuilder = new StringBuilder();
        matrixBuilder.append("\n======================================================");
        matrixBuilder.append("\n\t\t\t\t\tRealNumber\n" + county);
        for (int i = 0; i < categories.size() - 1; i++) {
            matrixBuilder.append(categories.get(i).getRealNumberOfItems() + "\t\t");
        }

        matrixBuilder.append("\n======================================================");
        int[][] matrix = confusionMatrix.getMatrix();
        matrixBuilder.append("\n\t\t\t\t\t  Matrix\n" + county);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                matrixBuilder.append(matrix[i][j]).append("\t\t");
            }
            matrixBuilder.append("\n");
        }
        log.info(matrixBuilder.toString());
    }
}
