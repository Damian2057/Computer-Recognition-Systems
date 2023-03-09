package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.CommonWords;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import java.util.List;

public class AverageWordLengthExtractor extends Extractor implements SpecificExtractor {
    @Override
    public Feature<?> extract(Article article) {
        List<String> words = CommonWords.remove(article);
        double avg = 0;
        for (String word : words) {
            avg += word.length();
        }
        avg /= words.size();

        return new Feature<Double>(Type.AVERAGE_WORD_LENGTH, avg);
    }
}
