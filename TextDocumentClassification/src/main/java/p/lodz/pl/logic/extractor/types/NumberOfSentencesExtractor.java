package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberOfSentencesExtractor implements SpecificExtractor {

    @Override
    public Feature<Double> extract(Article article) {
        String text = article.getBody();

        Pattern pattern = Pattern.compile("\\.\\s");
        Matcher matcher = pattern.matcher(text);
        double count = 0;
        while (matcher.find()) {
            count++;
        }

        return new Feature<Double>(Type.NUMBER_OF_SENTENCES, count);
    }
}
