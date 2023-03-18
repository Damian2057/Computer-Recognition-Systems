package p.lodz.pl.logic.quality;

import p.lodz.pl.model.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ConfusionMatrix implements Matrix {

    private List<ClassificationCategory> categories = new ArrayList<>();
    private List<Vector> vectors;

    public List<ClassificationCategory> measureClassificationQuality(List<Vector> vectors) {
        this.vectors = vectors;
        this.categories = detectCategories();

        double acc = calculateAcc();
        categories.forEach(category -> {
            category.setAcc(acc);
            category.setPositivelyCorrect(calculatePositivelyCorrect(category.getType()));
            category.setAllClassifiedToCategory(calculateAllClassifiedToCategory(category.getType()));
            category.setRealNumberOfItems(calculateRealNumberOfCategoryItems(category.getType()));
        });

        categories.forEach(ClassificationCategory::calculate);
        return categories;
    }

    private List<ClassificationCategory> detectCategories() {
        Set<String> set = vectors.stream()
                .map(Vector::getArticleRealCountry)
                .collect(Collectors.toSet());
        return set.stream().map(ClassificationCategory::new).toList();
    }

    private double calculateAcc() {
        int truePositive;
        int size = vectors.size();
        truePositive = (int) vectors.stream()
                .filter(article -> article.getClassificationCountry().equals(article.getArticleRealCountry()))
                .count();
        return 1.0 * truePositive / size;
    }

    private int calculatePositivelyCorrect(String type) {
        return (int) vectors.stream().filter(article -> article.getClassificationCountry()
                .equals(article.getArticleRealCountry()) && article.getArticleRealCountry().equals(type)).count();
    }

    private int calculateAllClassifiedToCategory(String type) {
        return (int) vectors.stream().filter(article -> article.getClassificationCountry()
                .equals(type)).count();
    }

    private int calculateRealNumberOfCategoryItems(String type) {
        return (int) vectors.stream().filter(article -> article.getArticleRealCountry()
                .equals(type)).count();
    }
}
