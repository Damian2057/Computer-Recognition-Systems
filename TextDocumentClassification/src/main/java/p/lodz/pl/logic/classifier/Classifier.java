package p.lodz.pl.logic.classifier;

import p.lodz.pl.model.Vector;

import java.util.List;

public interface Classifier {
    List<Vector> classifyTestSet();
}
