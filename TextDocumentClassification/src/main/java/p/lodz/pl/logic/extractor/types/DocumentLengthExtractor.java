package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.FilterHelper;
import p.lodz.pl.logic.extractor.types.base.SpecificExtractor;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import java.util.List;

import static p.lodz.pl.constants.Const.COMMON_WORDS_DICTIONARY;

public class DocumentLengthExtractor implements SpecificExtractor {
    @Override
    public Feature<Double> extract(Article article) {
        List<String> words = FilterHelper.removeWordsFromDictionary(article, COMMON_WORDS_DICTIONARY);
        return new Feature<>(Type.DOCUMENT_LENGTH, (double) words.size());
    }
}
