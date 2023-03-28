package p.lodz.pl.logic.extractor;

import lombok.extern.java.Log;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;
import p.lodz.pl.dao.ArticleLoader;
import p.lodz.pl.dao.ArticleLoaderImpl;
import p.lodz.pl.enums.Type;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Vector;

import java.util.ArrayList;
import java.util.List;

import static p.lodz.pl.constants.Const.ARTICLES;

@Log
public class FeatureExtractor implements Extractor {

    private static final ArticleLoader<Article> loader = new ArticleLoaderImpl(ARTICLES.getName());
    private static final Properties prop = Config.getProperties();
    private final List<Article> articles;

    public FeatureExtractor() {
        this.articles = loader.read();
    }

    @Override
    public List<Vector> extract() {
        List<Vector> vectors = new ArrayList<>();
        final int size = articles.size();
        int i = 0;
        for (Article article : articles) {
            try {
                log.info(Math.round((i++ * 1.0 / size) * 100.0) + " % percent vectors created");
                Vector vector = new Vector(article.getPlace());
                if (prop.isCurrencyExtractor()) {
                    vector.addFeature(Type.CURRENCY.extract(article));
                }
                if (prop.isNumberOfSentencesExtractor()) {
                    vector.addFeature(Type.NUMBER_OF_SENTENCES.extract(article));
                }
                if (prop.isHistoricalFigureExtractor()) {
                    vector.addFeature(Type.HISTORICAL_FIGURES.extract(article));
                }
                if (prop.isPlacesExtractor()) {
                    vector.addFeature(Type.PLACES.extract(article));
                }
                if (prop.isCountryExtractor()) {
                    vector.addFeature(Type.COUNTRY.extract(article));
                }
                if (prop.isFrequencyUniquenessWordsExtractor()) {
                    vector.addFeature(Type.FREQUENCY_NUMBER_OF_UNIQUENESS_WORDS.extract(article));
                }
                if (prop.isNumberOfLongWordsExtractor()) {
                    vector.addFeature(Type.NUMBER_OF_WORD_N_LENGTH.extract(article));
                }
                if (prop.isAverageWordLengthExtractor()) {
                    vector.addFeature(Type.AVERAGE_WORD_LENGTH.extract(article));
                }
                if (prop.isKeyWordExtractor()) {
                    vector.addFeature(Type.KEY_WORD.extract(article));
                }
                if (prop.isExceptKeyWordExtractor()) {
                    vector.addFeature(Type.EXCEPT_KEY_WORD.extract(article));
                }
                if (prop.isFrequencyCommonWordExtractor()) {
                    vector.addFeature(Type.FREQUENCY_OF_MOST_COMMON_WORD.extract(article));
                }
                if (prop.isCapitalWordExtractor()) {
                    vector.addFeature(Type.CAPITAL_WORD.extract(article));
                }
                if (prop.isDocumentLengthExtractor()) {
                    vector.addFeature(Type.DOCUMENT_LENGTH.extract(article));
                }
                vectors.add(vector);
            } catch (Exception e) {
                throw new RuntimeException("feature extraction problem for article:" + article.toString(), e);
            }
        }
        normalizeVector(vectors);

        log.info("All vectors are ready");
        return vectors;
    }

    private void normalizeVector(List<Vector> vectors) {
        log.info("Vector normalization");
        int numberOfFeatures = vectors.get(0).getFeatures().size();
        for (int i = 0; i < numberOfFeatures; i++) {
            if (vectors.get(i).getFeatures().get(i).getFeature() instanceof Double) {
                double max = Double.MIN_VALUE;
                for (Vector vector : vectors) {
                    double value = (double) vector.getFeatures().get(i).getFeature();
                    max = Math.max(value, max);
                }
                for (Vector vector : vectors) {
                    double value = (double) vector.getFeatures().get(i).getFeature() / max;
                    vector.getFeatures().get(i).setFeature(value);
                }
            }
        }
    }
}
