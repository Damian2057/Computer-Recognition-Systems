package p.lodz.pl.linguisticsummaries;

import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import p.lodz.pl.linguisticsummaries.view.config.GraphicalApplication;

@SpringBootApplication
public class LinguisticSummariesApplication {

    public static void main(String[] args) {
        Application.launch(GraphicalApplication.class, args);
    }
}
