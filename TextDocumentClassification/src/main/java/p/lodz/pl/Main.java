package p.lodz.pl;

import p.lodz.pl.logic.extractor.Extractor;
import p.lodz.pl.logic.extractor.FeatureExtractor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        Extractor extractor = new FeatureExtractor();
        var vectors = extractor.extract();
    }
}