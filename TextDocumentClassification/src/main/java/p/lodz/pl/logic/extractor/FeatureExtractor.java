package p.lodz.pl.logic.extractor;

import p.lodz.pl.dao.ArticleLoader;
import p.lodz.pl.dao.Loader;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Vector;

import java.util.List;

import static p.lodz.pl.constants.Const.ARTICLES;

public class FeatureExtractor implements Extractor {

    private static final Loader<Article> articles = new ArticleLoader(ARTICLES.name());

    @Override
    public List<Vector> extract() {
        return null;
    }
}
