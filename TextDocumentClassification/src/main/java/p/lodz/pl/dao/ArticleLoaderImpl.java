package p.lodz.pl.dao;

import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import p.lodz.pl.config.Config;
import p.lodz.pl.config.Properties;
import p.lodz.pl.model.Article;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import static p.lodz.pl.constants.Const.*;

@Log
public class ArticleLoaderImpl implements ArticleLoader<Article> {

    private static final Properties prop = Config.getProperties();
    private final String regex;
    private final List<Document> documents;

    public ArticleLoaderImpl(String path) {
        this.regex = prop.getRegex();
        if (prop.getTestMode().isEnable()) {
            try {
                this.documents = drawDocuments(path, prop.getTestMode().getNumberOfArticles());
            } catch (Exception e) {
                throw new RuntimeException(e);
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
        log.info("Load: " + documents.size() + " documents");
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

        return articles;
    }

    private List<Document> drawDocuments(String path, int count) throws IOException {
        List<Document> documents = new ArrayList<>();
        File[] files = new File(path).listFiles();
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            documents.add(Jsoup.parse(files[rand.nextInt(files.length)], "UTF-8", ""));
        }
        return documents;
    }
}
