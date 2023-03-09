package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.CommonWords;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import java.util.List;

import static p.lodz.pl.constants.Const.COMMON_WORDS_DICTIONARY;

public class WordLengthExtractor implements SpecificExtractor {

    private final int wordLength;

    public WordLengthExtractor(int wordLength) {
        this.wordLength = wordLength;
    }

    @Override
    public Feature<?> extract(Article article) {
        List<String> words = CommonWords.removeByDictionary(article, COMMON_WORDS_DICTIONARY);
        double count = 0;
        for (String word : words) {
            if (word.length() >= wordLength) {
                count++;
            }
        }

        return new Feature<Double>(Type.NUMBER_OF_WORD_LENGTH, count);
    }
}
