package p.lodz.pl.dao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import p.lodz.pl.model.Article;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static p.lodz.pl.constants.Const.*;

public class ArticleLoader implements Loader {

    private static final Logger LOGGER = LogManager.getLogManager().getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String REGEX = "(?i)west-germany|usa|france|uk|canada|japan";
    private final String path;
    private final List<Document> documents;

    public ArticleLoader(String path) throws IOException {
        this.path = path;
//        this.documents = Arrays.stream(Objects.requireNonNull(new File(path).listFiles())).map(file -> {
//            try {
//                return Jsoup.parse(file, "UTF-8", "");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }).toList();
        this.documents = List.of(Jsoup.parse(new File("src/main/resources/articles/reut2-000.sgm"), "UTF-8", ""));
    }

    @Override
    public List<Article> read() throws FileNotFoundException {
        List<Article> articles = new ArrayList<>();
        for (Document document : documents) {
            Elements elements = document.select(REUTERS.name());
            for (Element element : elements) {
                Article.ArticleBuilder builder = Article.builder();

                String date = element.select(DATE.name()).text();
                builder.date(date);

                String[] topics = element.select(TOPICS.name()).select(D.name()).text().split(SPACE.name());
                builder.topics(List.of(topics));

                String[] places = element.select(PLACES.name()).select(D.name()).text().split(SPACE.name());
                if (places.length == 0 || Arrays.stream(places).anyMatch(country -> country.matches(REGEX))) {
                    continue;
                } else {
                    builder.places(List.of(places));
                }

                String[] peoples = element.select(PEOPLE.name()).select(D.name()).text().split(SPACE.name());
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
