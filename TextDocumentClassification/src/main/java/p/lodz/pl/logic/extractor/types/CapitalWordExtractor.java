package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.extractor.types.base.SpecificExtractor;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import java.util.stream.IntStream;

public class CapitalWordExtractor implements SpecificExtractor {
    @Override
    public Feature<Double> extract(Article article) {
        String text = article.getBody();
        try {
            String[] words = text.split("\\s+");
            double count = IntStream.range(1, words.length)
                    .filter(i -> Character.isUpperCase(words[i].charAt(0)) && words[i-1].charAt(words[i-1].length()-1) != '.')
                    .count();

            return new Feature<>(Type.CAPITAL_WORD, count);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
