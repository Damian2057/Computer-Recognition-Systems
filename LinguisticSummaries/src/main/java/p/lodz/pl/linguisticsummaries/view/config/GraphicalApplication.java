package p.lodz.pl.linguisticsummaries.view.config;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;
import p.lodz.pl.linguisticsummaries.LinguisticSummariesApplication;

@Component
public class GraphicalApplication extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void start(Stage stage) {
        context.publishEvent(new StageReadyEvent(stage));
    }

    @Override
    public void init() {
        context = new SpringApplicationBuilder(LinguisticSummariesApplication.class).run();
    }

    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }

    static class StageReadyEvent extends ApplicationEvent {
        public StageReadyEvent(Stage stage) {
            super(stage);
        }

        public Stage getStage() {
            return (Stage) getSource();
        }
    }
}
