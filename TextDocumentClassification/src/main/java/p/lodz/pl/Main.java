package p.lodz.pl;

import p.lodz.pl.logic.extractor.Extractor;
import p.lodz.pl.logic.extractor.FeatureExtractor;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
//        ArticleLoader articleLoader = new ArticleLoader(ARTICLES.getName());
//        articleLoader.read();
//        final Properties prop = Config.getProperties();
//        System.out.println(prop.isCurrency());

        Extractor extractor = new FeatureExtractor();
        var vectors = extractor.extract();
        var feature  = vectors.get(0).getFeatures().get(0).getFeature();
    }
}