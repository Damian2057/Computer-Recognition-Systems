package p.lodz.pl.frontend.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MultiSubjectController {
    public AnchorPane scrollAttributes;
    public Button generateButton;
    public AnchorPane scrollQuantifiers;
    public TableView summaryTableView;
    public TableColumn formColumn;
    public TableColumn summaryColumn;
    public TableColumn degreeColumn;
    public ChoiceBox firstSubjectChoiceBox;
    public ChoiceBox secondSubjectChoiceBox;
    public Button AdvancedSettingsButton;
    public Button singleSubjectButton;

    private Scene previousScene;

    public void generateMultiSubjectSummaries(ActionEvent event) {
    }

    public void goToAdvancedSettings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/advanced.fxml"));
            Parent advancedRoot = loader.load();
            AdvancedController advancedController = loader.getController();

            Scene currentScene = ((Node) event.getSource()).getScene();

            advancedController.setPreviousScene(currentScene);

            Scene advancedScene = new Scene(advancedRoot);

            Stage currentStage = (Stage) currentScene.getWindow();

            currentStage.setScene(advancedScene);

            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToSingleSubject(ActionEvent event) {
        try {
            Parent advancedRoot = FXMLLoader.load(getClass().getResource("/components/view.fxml"));
            Scene advancedScene = new Scene(advancedRoot);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(advancedScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
