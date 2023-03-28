package p.lodz.pl.logic.classifier;

import lombok.extern.java.Log;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;
import p.lodz.pl.constants.Const;
import p.lodz.pl.logic.classifier.metrics.Metric;
import p.lodz.pl.logic.classifier.metrics.MetricFactory;
import p.lodz.pl.model.Vector;

import java.util.*;
import java.util.stream.Collectors;

import static p.lodz.pl.constants.Const.TEST;
import static p.lodz.pl.constants.Const.TRAINING;

@Log
public class KNNClassifier implements Classifier {

    private static final Properties prop = Config.getProperties();
    private final List<Vector> trainingSet;
    private final List<Vector> testSet;
    private final Metric metric;

    public KNNClassifier(List<Vector> vectors) {
        Map<Const, List<Vector>> map = splitSet(vectors);
        MetricFactory factory = new MetricFactory();
        this.trainingSet = map.get(TRAINING);
        this.testSet = map.get(TEST);
        this.metric = factory.getMetric(prop.getMetric());
    }

    @Override
    public List<Vector> classifyTestSet() {
        for (Vector testVector : testSet) {
            new Thread(() -> {
                List<Neighbor> neighbors = new ArrayList<>();
                for (Vector trainingVector : trainingSet) {
                    neighbors.add(metric.calculateMetric(testVector, trainingVector));
                }
                testVector.setClassificationCountry(getClassByKNN(neighbors));
            }).start();
        }

        return testSet;
    }

    private Map<Const, List<Vector>> splitSet(List<Vector> vectors) {
        int size = vectors.size();
        int mid = (int) Math.ceil(size * prop.getProportionOfDataSets()[0] * 0.01);

        List<Vector> firstList = new ArrayList<>(vectors.subList(0, mid));
        List<Vector> secondList = new ArrayList<>(vectors.subList(mid, size));

        Map<Const, List<Vector>> resultMap = new HashMap<>();
        resultMap.put(TRAINING, firstList);
        resultMap.put(TEST, secondList);

        return resultMap;
    }

    private String getClassByKNN(List<Neighbor> neighbors) {
        List<Neighbor> kNearest = neighbors.stream()
                .sorted(Comparator.comparing(Neighbor::getDistance))
                .limit(prop.getK())
                .toList();
        Map<String, Long> countryCount = kNearest.stream()
                .collect(Collectors.groupingBy(Neighbor::getCountry,
                        Collectors.counting()));
        return countryCount.entrySet().stream()
                .max(Map.Entry.comparingByValue()).get().getKey();
    }
}
