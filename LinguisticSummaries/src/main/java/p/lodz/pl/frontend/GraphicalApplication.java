package p.lodz.pl.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicalApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicalApplication.class.getResource("/components/view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 750);
        stage.setTitle("Linguistic Summaries");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
