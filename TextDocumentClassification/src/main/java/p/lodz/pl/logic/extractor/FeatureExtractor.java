package p.lodz.pl.logic.extractor;

import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;
import p.lodz.pl.dao.ArticleLoader;
import p.lodz.pl.dao.Loader;
import p.lodz.pl.enums.Type;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Vector;

import java.util.ArrayList;
import java.util.List;

import static p.lodz.pl.constants.Const.ARTICLES;

public class FeatureExtractor implements Extractor {

    private static final Loader<Article> loader = new ArticleLoader(ARTICLES.name());
    private static final Properties prop = Config.getProperties();
    private final List<Article> articles;

    public FeatureExtractor() {
        this.articles = loader.read();
    }

    @Override
    public List<Vector> extract() {
        List<Vector> vectors = new ArrayList<>();
        for (Article article : articles) {
            Vector vector = new Vector();
            if (prop.isCurrency()) {
                vector.addFeature(Type.CURRENCY.getExtractor().extract(article));
            }
            if (prop.isNumberOfSentences()) {
                vector.addFeature(Type.NUMBER_OF_SENTENCES.getExtractor().extract(article));
            }

            vectors.add(vector);
        }
        return vectors;
    }
}
