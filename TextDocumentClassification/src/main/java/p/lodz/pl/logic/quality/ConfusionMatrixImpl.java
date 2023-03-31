package p.lodz.pl.logic.quality;

import p.lodz.pl.model.Vector;

import java.util.*;
import java.util.stream.Collectors;

public class ConfusionMatrixImpl implements ConfusionMatrix {

    private List<Vector> vectors;
    int[][] matrix = new int[6][6];

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
        calculateConfusionMatrix();

        return categories;
    }

    public int[][] getMatrix() {
        return matrix;
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
            preValue += category.getPre() * category.getRealNumberOfItems();
            recValue += category.getRec() * category.getRealNumberOfItems();
            f1Value += category.getF1() * category.getRealNumberOfItems();
        }
        int count = categories.stream().mapToInt(Category::getRealNumberOfItems).sum();
        Category category = new Category("Summary");
        category.setAcc(categories.get(0).getAcc());
        category.setPre(preValue / count);
        category.setRec(recValue / count);
        category.setF1(f1Value / count);

        return category;
    }

    private void calculateConfusionMatrix() {
        for (Vector vector : vectors) {
            int real = getCountryByIndex(vector.getArticleRealCountry());
            int pred = getCountryByIndex(vector.getClassificationCountry());
            this.matrix[real][pred] += 1;
        }
    }

    private int getCountryByIndex(String country) {
        switch (country) {
            case "usa" -> {
                return 0;
            }
            case "uk" -> {
                return 1;
            }
            case "canada" -> {
                return 2;
            }
            case "france" -> {
                return 3;
            }
            case "japan" -> {
                return 4;
            }
            case "west-germany" -> {
                return 5;
            }
            default -> {
                return -1;
            }
        }
    }

}
