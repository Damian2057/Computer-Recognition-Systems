package p.lodz.pl.logic.quality;

import p.lodz.pl.model.Vector;

import java.util.*;
import java.util.stream.Collectors;

public class ConfusionMatrixImpl implements ConfusionMatrix {

    private List<Vector> vectors;

    public List<Category> measureClassificationQuality(List<Vector> vectors) {
        this.vectors = vectors;
        List<Category> categories = new ArrayList<>(detectCategories());

        double acc = calculateAcc();
        categories.forEach(category -> {
            category.setAcc(acc);
            category.setPositivelyCorrect(calculatePositivelyCorrect(category.getType()));
            category.setAllClassified(calculateAllClassifiedToCategory(category.getType()));
            category.setRealNumberOfItems(calculateRealNumberOfCategoryItems(category.getType()));
        });

        categories.forEach(Category::calculate);
        Category summary = calculateAverageWeighted(categories);
        categories.add(summary);
        return categories;
    }

    private List<Category> detectCategories() {
        Set<String> set = vectors.stream()
                .map(Vector::getArticleRealCountry)
                .collect(Collectors.toSet());
        return set.stream().map(Category::new).toList();
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

    private Category calculateAverageWeighted(List<Category> categories) {
        double preValue = 0.0,
                recValue = 0.0,
                f1Value = 0.0;
        for (Category category : categories) {
            preValue += ifNan(category.getPre() * category.getAllClassified());
            recValue += ifNan(category.getRec() * category.getAllClassified());
            f1Value += ifNan(category.getF1() * category.getAllClassified());
        }
        int count = categories.stream().mapToInt(Category::getAllClassified).sum();
        Category category = new Category("Summary");
        category.setAcc(categories.get(0).getAcc());
        category.setPre(preValue / count);
        category.setRec(recValue / count);
        category.setF1(f1Value / count);

        return category;
    }

    private static double ifNan(Double Nan) {
        return Double.isNaN(Nan) ? 0.0 : Nan;
    }
}
