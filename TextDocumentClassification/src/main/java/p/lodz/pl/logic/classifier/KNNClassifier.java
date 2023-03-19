package p.lodz.pl.logic.classifier;

import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;
import p.lodz.pl.constants.Const;
import p.lodz.pl.model.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static p.lodz.pl.constants.Const.TEST;
import static p.lodz.pl.constants.Const.TRAINING;

public class KNNClassifier implements Classifier {

    private static final Properties prop = Config.getProperties();
    private final List<Vector> trainingSet;
    private final List<Vector> testSet;

    public KNNClassifier(List<Vector> vectors) {
        Map<Const, List<Vector>> map = splitSet(vectors);
        this.trainingSet = map.get(TRAINING);
        this.testSet = map.get(TEST);
    }

    public List<Vector> classifyTestSet() {
        for (Vector testVector : testSet) {

        }

        return testSet;
    }

    private Map<Const, List<Vector>> splitSet(List<Vector> vectors) {
        int size = vectors.size();
        int mid = (int) Math.ceil(size * prop.getProportionOfDataSets()[0]);

        List<Vector> firstList = new ArrayList<>(vectors.subList(0, mid));
        List<Vector> secondList = new ArrayList<>(vectors.subList(mid, size));

        Map<Const, List<Vector>> resultMap = new HashMap<>();
        resultMap.put(TRAINING, firstList);
        resultMap.put(TEST, secondList);

        return resultMap;
    }
}
