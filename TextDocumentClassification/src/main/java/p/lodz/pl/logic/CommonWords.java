package p.lodz.pl.logic;

import p.lodz.pl.dao.Dictionary;
import p.lodz.pl.dao.DictionaryReader;
import p.lodz.pl.model.Article;

import java.util.List;
import java.util.stream.Collectors;

import static p.lodz.pl.constants.Const.COMMON_WORDS_DICTIONARY;

public class CommonWords {

    private static final Dictionary DICTIONARY = new DictionaryReader();
    private static final String LETTER_REGEX = "[^a-zA-Z ]";

    public static List<String> remove(Article article) {
        String text = article.getBody();
        String regex = "\\b(" + String.join("|", DICTIONARY.read(COMMON_WORDS_DICTIONARY)) + ")\\b";
        text = text.replaceAll(LETTER_REGEX, "");
        text = text.toLowerCase().replaceAll(regex, "");
        List<String> splitted = List.of(text.split(" "));
        splitted = splitted.stream().filter(x -> !x.isEmpty()).collect(Collectors.toList());

        return splitted;
    }
}
