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
    private List<Article> articles;

    @Override
    public List<Vector> extract() {
        this.articles = loader.read();
        List<Vector> vectors = new ArrayList<>();
        final int size = articles.size();
        int i = 0;
        for (Article article : articles) {
            try {
                log.info(Math.round((i++ * 1.0 / size) * 100.0) + " % percent vectors created");
                Vector vector = new Vector(article.getPlace());
                vector.addFeature(Type.CURRENCY.extract(article));
                vector.addFeature(Type.NUMBER_OF_SENTENCES.extract(article));
                vector.addFeature(Type.HISTORICAL_FIGURES.extract(article));
                vector.addFeature(Type.PLACES.extract(article));
                vector.addFeature(Type.COUNTRY.extract(article));
                vector.addFeature(Type.FREQUENCY_NUMBER_OF_UNIQUENESS_WORDS.extract(article));
                vector.addFeature(Type.NUMBER_OF_WORD_N_LENGTH.extract(article));
                vector.addFeature(Type.AVERAGE_WORD_LENGTH.extract(article));
                vector.addFeature(Type.KEY_WORD.extract(article));
                vector.addFeature(Type.EXCEPT_KEY_WORD.extract(article));
                vector.addFeature(Type.FREQUENCY_OF_MOST_COMMON_WORD.extract(article));
                vector.addFeature(Type.CAPITAL_WORD.extract(article));
                vector.addFeature(Type.DOCUMENT_LENGTH.extract(article));

                vectors.add(vector);
            } catch (Exception e) {
                throw new RuntimeException("feature extraction problem for article:" + article.toString(), e);
            }
        }
        normalizeVector(vectors);

        log.info("All vectors are ready");
        return vectors;
    }

    @Override
    public List<Vector> reExtract(List<Vector> vectors) {
        List<Vector> reExtractVectors = new ArrayList<>();
        for (Vector vector : vectors) {
            Vector reVector = new Vector(vector.getArticleRealCountry());
            if (prop.isCurrencyExtractor()) {
                reVector.addFeature(vector.getFeatures().get(0));
            }
            if (prop.isNumberOfSentencesExtractor()) {
                reVector.addFeature(vector.getFeatures().get(1));
            }
            if (prop.isHistoricalFigureExtractor()) {
                reVector.addFeature(vector.getFeatures().get(2));
            }
            if (prop.isPlacesExtractor()) {
                reVector.addFeature(vector.getFeatures().get(3));
            }
            if (prop.isCountryExtractor()) {
                reVector.addFeature(vector.getFeatures().get(4));
            }
            if (prop.isFrequencyUniquenessWordsExtractor()) {
                reVector.addFeature(vector.getFeatures().get(5));
            }
            if (prop.isNumberOfLongWordsExtractor()) {
                reVector.addFeature(vector.getFeatures().get(6));
            }
            if (prop.isAverageWordLengthExtractor()) {
                reVector.addFeature(vector.getFeatures().get(7));
            }
            if (prop.isKeyWordExtractor()) {
                reVector.addFeature(vector.getFeatures().get(8));
            }
            if (prop.isExceptKeyWordExtractor()) {
                reVector.addFeature(vector.getFeatures().get(9));
            }
            if (prop.isFrequencyCommonWordExtractor()) {
                reVector.addFeature(vector.getFeatures().get(10));
            }
            if (prop.isCapitalWordExtractor()) {
                reVector.addFeature(vector.getFeatures().get(11));
            }
            if (prop.isDocumentLengthExtractor()) {
                reVector.addFeature(vector.getFeatures().get(12));
            }
            reExtractVectors.add(reVector);

        }
        return reExtractVectors;
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
