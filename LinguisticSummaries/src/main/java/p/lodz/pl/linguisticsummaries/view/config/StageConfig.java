package p.lodz.pl.linguisticsummaries.view.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StageConfig implements ApplicationListener<GraphicalApplication.StageReadyEvent> {

    @Value("classpath:/view/stage.fxml")
    private Resource resource;

    @Override
    public void onApplicationEvent(GraphicalApplication.StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL());
            Parent parent = fxmlLoader.load();
            Stage stage = event.getStage();
            stage.setScene(new Scene(parent, 800, 600));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
