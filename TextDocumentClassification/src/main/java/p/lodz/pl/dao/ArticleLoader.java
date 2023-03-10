package p.lodz.pl.dao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import p.lodz.pl.model.Article;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static p.lodz.pl.constants.Const.*;

public class ArticleLoader implements Loader<Article> {

    private static final Logger LOGGER = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String REGEX = "(?i)west-germany|usa|france|uk|canada|japan";
    private final String path;
    private final List<Document> documents;

    public ArticleLoader(String path) {
        this.path = path;
//        this.documents = Arrays.stream(Objects.requireNonNull(new File(path).listFiles())).map(file -> {
//            try {
//                return Jsoup.parse(file, "UTF-8", "");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }).toList();
        try {
            this.documents = List.of(Jsoup.parse(new File("src/main/resources/articles/reut2-000.sgm"), "UTF-8", ""));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<Article> read() {
        List<Article> articles = new ArrayList<>();
        for (Document document : documents) {
            Elements elements = document.select(REUTERS.name());
            for (Element element : elements) {
                Article.ArticleBuilder builder = Article.builder();

                String[] topics = element.select(TOPICS.name()).select(D.name()).text().split(SPACE.getName());
                builder.topics(List.of(topics));

                String[] place = element.select(PLACES.name()).select(D.name()).text().split(SPACE.getName());
                Optional<String> elem = Arrays.stream(place).filter(country -> country.matches(REGEX)).findAny();
                if (elem.isEmpty()) {
                    continue;
                }
                elem.ifPresent(builder::place);

                String[] peoples = element.select(PEOPLE.name()).select(D.name()).text().split(SPACE.getName());
                builder.peoples(List.of(peoples));

                String title = element.select(TITLE.name()).text();
                builder.title(title);

                String body = element.select(TEXT.name()).text();
                if (body.contains("blah")) {
                    continue;
                } else {
                    builder.body(body);
                }

                articles.add(builder.build());
            }
        }
        LOGGER.info(String.format("%s data loaded", articles.size()));

        return articles;
    }
}
