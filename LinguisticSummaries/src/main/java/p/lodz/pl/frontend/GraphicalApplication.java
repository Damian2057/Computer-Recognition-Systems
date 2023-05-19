package p.lodz.pl.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GraphicalApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(GraphicalApplication.class.getResource("/components/view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1225, 740);
        stage.setTitle("CPS");
        stage.setScene(scene);
        stage.show();
    }
}
