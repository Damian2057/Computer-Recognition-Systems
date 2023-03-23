package p.lodz.pl.dao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;
import p.lodz.pl.model.Article;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static p.lodz.pl.constants.Const.*;

public class ArticleLoaderImpl implements ArticleLoader<Article> {

    private static final Logger LOGGER = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final Properties prop = Config.getProperties();
    private final String regex;
    private final List<Document> documents;

    public ArticleLoaderImpl(String path) {
        this.regex = prop.getRegex();
        if (prop.isTestMode()) {
            try {
                this.documents = List.of(Jsoup.parse(new File("src/main/resources/articles/reut2-000.sgm"), "UTF-8", ""));
            } catch (Exception e) {
                throw new RuntimeException();
            }
        } else {
            this.documents = Arrays.stream(Objects.requireNonNull(new File(path).listFiles())).map(file -> {
                try {
                    return Jsoup.parse(file, "UTF-8", "");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).toList();
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
                Optional<String> elem = Arrays.stream(place).filter(country -> country.matches(regex)).findAny();
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
