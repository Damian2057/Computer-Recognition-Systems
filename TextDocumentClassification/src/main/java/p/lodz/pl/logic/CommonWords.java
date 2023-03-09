package p.lodz.pl.logic;

import p.lodz.pl.dao.Dictionary;
import p.lodz.pl.dao.DictionaryReader;
import p.lodz.pl.model.Article;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static p.lodz.pl.constants.Const.COMMON_WORDS_DICTIONARY;

public class CommonWords {

    private static final Dictionary DICTIONARY = new DictionaryReader();
    private static final String LETTER_REGEX = "[^a-zA-Z ]";

    public static List<String> remove(Article article) {
        String text = article.getBody();
        text = text.replaceAll(LETTER_REGEX, "");

        Pattern pattern = Pattern.compile("\\b(" + String.join("|",
                DICTIONARY.read(COMMON_WORDS_DICTIONARY)) + ")\\b",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        text = matcher.replaceAll("");

        List<String> splitted = List.of(text.split(" "));
        splitted = splitted.stream().filter(x -> !x.isEmpty()).collect(Collectors.toList());

        return splitted;
    }

    public static List<String> simpleRemove(Article article) {
        String text = article.getBody();
        text = text.replaceAll(LETTER_REGEX, "");
        List<String> splitted = List.of(text.split(" "));
        splitted = splitted.stream().filter(x -> !x.isEmpty()).collect(Collectors.toList());

        return splitted;
    }
}
