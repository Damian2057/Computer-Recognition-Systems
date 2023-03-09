package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.CommonWords;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import java.util.List;

import static p.lodz.pl.constants.Const.COMMON_WORDS_DICTIONARY;

public class AverageWordLengthExtractor implements SpecificExtractor {
    @Override
    public Feature<?> extract(Article article) {
        List<String> words = CommonWords.removeByDictionary(article, COMMON_WORDS_DICTIONARY);
        double avg = 0;
        for (String word : words) {
            avg += word.length();
        }
        avg /= words.size();

        return new Feature<Double>(Type.AVERAGE_WORD_LENGTH, avg);
    }
}
