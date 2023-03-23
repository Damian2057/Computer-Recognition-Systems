package p.lodz.pl.logic.quality;

import p.lodz.pl.model.Vector;

import java.util.List;

public interface ConfusionMatrix {
    List<Category> measureClassificationQuality(List<Vector> vectors);
}
