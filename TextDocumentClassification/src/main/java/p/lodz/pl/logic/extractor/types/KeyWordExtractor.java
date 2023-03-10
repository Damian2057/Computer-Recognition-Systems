package p.lodz.pl.logic.extractor.types;

import p.lodz.pl.enums.Type;
import p.lodz.pl.logic.extractor.types.base.DictionaryExtractor;
import p.lodz.pl.logic.extractor.types.base.SpecificExtractor;
import p.lodz.pl.model.Article;
import p.lodz.pl.model.Feature;

import static p.lodz.pl.constants.Const.KEY_WORDS_DICTIONARY;

public class KeyWordExtractor extends DictionaryExtractor implements SpecificExtractor {

    @Override
    public Feature<String> extract(Article article) {
        return new Feature<>(Type.KEY_WORD,
                mostCommonItem(article, KEY_WORDS_DICTIONARY).orElse("undefined"));
    }
}
