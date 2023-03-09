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
            if (prop.isCurrencyExtractor()) {
                vector.addFeature(Type.CURRENCY.getExtractor().extract(article));
            }
            if (prop.isNumberOfSentencesExtractor()) {
                vector.addFeature(Type.NUMBER_OF_SENTENCES.getExtractor().extract(article));
            }
            if (prop.isHistoricalFigureExtractor()) {
                vector.addFeature(Type.HISTORICAL_FIGURES.getExtractor().extract(article));
            }
            if (prop.isPlacesExtractor()) {
                vector.addFeature(Type.PLACES.getExtractor().extract(article));
            }
            if (prop.isCountryExtractor()) {
                vector.addFeature(Type.COUNTRY.getExtractor().extract(article));
            }
            if (prop.isFrequencyUniquenessWordsExtractor()) {
                vector.addFeature(Type.FREQUENCY_NUMBER_OF_UNIQUENESS_WORDS.getExtractor().extract(article));
            }
            if (prop.isNumberOfLongWordsExtractor()) {
                vector.addFeature(Type.NUMBER_OF_WORD_N_LENGTH.getExtractor().extract(article));
            }
            if (prop.isAverageWordLengthExtractor()) {
                vector.addFeature(Type.AVERAGE_WORD_LENGTH.getExtractor().extract(article));
            }
            if (prop.isKeyWordExtractor()) {
                vector.addFeature(Type.KEY_WORD.getExtractor().extract(article));
            }
            if (prop.isExceptKeyWordExtractor()) {
                vector.addFeature(Type.EXCEPT_KEY_WORD.getExtractor().extract(article));
            }
            if (prop.isFrequencyCommonWordExtractor()) {
                vector.addFeature(Type.FREQUENCY_OF_MOST_COMMON_WORD.getExtractor().extract(article));
            }
            if (prop.isCapitalWordExtractor()) {
                vector.addFeature(Type.CAPITAL_WORD.getExtractor().extract(article));
            }
            if (prop.isDocumentLengthExtractor()) {
                vector.addFeature(Type.DOCUMENT_LENGTH.getExtractor().extract(article));
            }
            vectors.add(vector);
        }
        return vectors;
    }
}
