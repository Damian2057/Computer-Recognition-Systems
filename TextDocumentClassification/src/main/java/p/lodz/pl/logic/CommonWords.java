package p.lodz.pl.logic;

import p.lodz.pl.constants.Const;
import p.lodz.pl.dao.Dictionary;
import p.lodz.pl.dao.DictionaryReader;
import p.lodz.pl.model.Article;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static p.lodz.pl.constants.Const.COMMON_WORDS_DICTIONARY;

public class CommonWords {

    private static final Dictionary DICTIONARY = new DictionaryReader();
    private static final String LETTER_REGEX = "[^a-zA-Z ]";

    public static List<String> removeByDictionary(Article article, Const path) {
        String text = simpleFilter(article);
        text = dictionaryFilter(text, path);

        List<String> splitted = List.of(text.split(" "));
        splitted = splitted.stream().filter(x -> !x.isEmpty()).collect(Collectors.toList());

        return splitted;
    }

    public static List<String> removeOnlyByDictionary(Article article, Const path) {
        String body = article.getBody();
        String text = dictionaryFilter(body, path);

        List<String> splitted = List.of(text.split(" "));
        splitted = splitted.stream().filter(x -> !x.isEmpty()).collect(Collectors.toList());

        return splitted;
    }

    public static List<String> applyDictionary(Article article, Const path) {
        String text = simpleFilter(article);
        List<String> keyWords = DICTIONARY.read(path);

        String filteredText = Arrays.stream(text.toLowerCase().split("\\s+"))
                .filter(keyWords::contains)
                .collect(Collectors.joining(" "));

        List<String> splitted = List.of(filteredText.split(" "));
        splitted = splitted.stream().filter(x -> !x.isEmpty()).collect(Collectors.toList());

        return splitted;
    }

    public static List<String> removeSpecialSymbols(Article article) {
        String text = simpleFilter(article);
        List<String> splitted = List.of(text.split(" "));
        splitted = splitted.stream().filter(x -> !x.isEmpty()).collect(Collectors.toList());

        return splitted;
    }
    private static String simpleFilter(Article article) {
        String text = article.getBody();
        text = text.replaceAll(LETTER_REGEX, "");
        return text;
    }

    private static String dictionaryFilter(String text, Const path) {
        Pattern pattern = Pattern.compile("\\b(" + String.join("|",
                        DICTIONARY.read(path)) + ")\\b",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(text);
        text = matcher.replaceAll("");
        return text;
    }
}
