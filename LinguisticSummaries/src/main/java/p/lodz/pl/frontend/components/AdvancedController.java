package p.lodz.pl.frontend.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdvancedController {
    public Button goBackButton;
    public AnchorPane summarizerLabelsPane;
    public TitledPane quantifierLabelsPane;
    public AnchorPane quantifierLabels;
    public AnchorPane addAnchorPane;
    public ChoiceBox addAttributeTypeChoiceBox;
    public TextField addEtiquetteNameTextField;
    public ChoiceBox addAttributeNameChoiceBox;
    public ChoiceBox addFunctionTypeChoiceBox;
    public Button addButton;
    public AnchorPane editAnchorPane;
    public ChoiceBox editAttributeTypeChoiceBox;
    public ChoiceBox editAttributeNameChoiceBox;
    public ChoiceBox editFunctionTypeChoiceBox;
    public Button editButton;
    public ChoiceBox editEtiquetteNameChoiceBox;
    public AnchorPane removeAnchorPane;
    public ChoiceBox removeAttributeTypeChoiceBox;
    public ChoiceBox removeAttributeNameChoiceBox;
    public ChoiceBox removeEtiquetteNameChoiceBox;
    public Button removeButton;

    private Scene previousScene;

    public void setPreviousScene(Scene scene) {
        previousScene = scene;
    }

    public void goToPreviousView(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (previousScene != null) {
            currentStage.setScene(previousScene);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/view.fxml"));
            Parent viewRoot = loader.load();
            Scene viewScene = new Scene(viewRoot);
            currentStage.setScene(viewScene);
        }

        currentStage.show();
    }
}
