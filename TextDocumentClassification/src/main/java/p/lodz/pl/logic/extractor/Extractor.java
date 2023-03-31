package p.lodz.pl.logic.extractor;

import p.lodz.pl.model.Vector;

import java.util.List;

public interface Extractor {

    List<Vector> extract();
    List<Vector> reExtract(List<Vector> vectors);
}
