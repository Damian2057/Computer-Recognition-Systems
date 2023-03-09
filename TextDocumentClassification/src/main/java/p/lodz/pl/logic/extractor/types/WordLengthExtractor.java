package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.CommonWords;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import java.util.List;

public class WordLengthExtractor implements SpecificExtractor {

    private final int wordLength;

    public WordLengthExtractor(int wordLength) {
        this.wordLength = wordLength;
    }

    @Override
    public Feature<?> extract(Article article) {
        List<String> words = CommonWords.remove(article);
        double count = 0;
        for (String word : words) {
            if (word.length() >= wordLength) {
                count++;
            }
        }

        return new Feature<Double>(Type.AVERAGE_WORD_LENGTH, count);
    }
}
