package p.lodz.pl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import p.lodz.pl.view.controller.MainStage;

@SpringBootApplication
public class MainApp extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(MainStage.class.getResource("/stages/MainStage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1225, 740);
        stage.setTitle("LSDA");
        stage.setScene(scene);
        stage.show();
    }
}
