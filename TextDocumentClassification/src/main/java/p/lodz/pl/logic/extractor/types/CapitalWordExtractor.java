package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.extractor.types.base.SpecificExtractor;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import java.util.Arrays;

public class CapitalWordExtractor implements SpecificExtractor {
    @Override
    public Feature<Double> extract(Article article) {
        String text = article.getBody();
        double count = Arrays.stream(text.split("\\.\\s+"))
                .flatMap(sentence -> Arrays.stream(sentence.split("\\s+")))
                .skip(1)
                .filter(word -> Character.isUpperCase(word.charAt(0)))
                .count();

        return new Feature<>(Type.CAPITAL_WORD, count);
    }
}
