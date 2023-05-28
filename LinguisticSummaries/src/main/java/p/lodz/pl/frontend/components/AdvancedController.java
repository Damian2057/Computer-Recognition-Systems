package p.lodz.pl.frontend.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticVariable;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;
import p.lodz.pl.backend.model.PolicyEntity;
import p.lodz.pl.backend.repository.MockRepository;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AdvancedController implements Initializable {
    @FXML
    private Button goBackButton;
    @FXML
    private AnchorPane summarizerLabelsPane;
    @FXML
    private AnchorPane quantifierLabelsPane;
    @FXML
    private AnchorPane addAnchorPane;
    @FXML
    private ChoiceBox<String> addAttributeTypeChoiceBox;
    @FXML
    private TextField addEtiquetteNameTextField;
    @FXML
    private ChoiceBox<String> addAttributeNameChoiceBox;
    @FXML
    private ChoiceBox<String> addFunctionTypeChoiceBox;
    @FXML
    private Button addButton;
    @FXML
    private AnchorPane editAnchorPane;
    @FXML
    private ChoiceBox<String> editAttributeTypeChoiceBox;
    @FXML
    private ChoiceBox<String> editAttributeNameChoiceBox;
    @FXML
    private ChoiceBox<String> editFunctionTypeChoiceBox;
    @FXML
    private Button editButton;
    @FXML
    private ChoiceBox<String> editEtiquetteNameChoiceBox;
    @FXML
    private AnchorPane removeAnchorPane;
    @FXML
    private ChoiceBox<String> removeAttributeTypeChoiceBox;
    @FXML
    private ChoiceBox<String> removeAttributeNameChoiceBox;
    @FXML
    private ChoiceBox removeEtiquetteNameChoiceBox;
    @FXML
    private Button removeButton;

    private static final String SUMMARIZER = "summarizer";
    private static final String QUANTIFIER = "quantifier";
    private static final String TRAPEZOIDAL = "trapezoidal";
    private static final String TRIANGULAR = "triangular";
    private static final String GAUSSIAN = "gaussian";
    private static final String  POLICY_TENURE = "policy tenure";
    private static final String  AGE_OF_CAR = "age of Car";
    private static final String  AGE_OF_POLICYHOLDER = "age of policyholder";
    private static final String  POPULATION_DENSITY = "population density";
    private static final String  DISPLACEMENT = "engine displacement";
    private static final String  TURNING_RADIUS = "turning radius";
    private static final String  GROSS_WEIGHT = "gross weight";
    private static final String  LENGTH = "length";
    private static final String  WIDTH = "width";
    private static final String  HEIGHT = "height";

    private Scene previousScene;

    private final MockRepository mockRepository = new MockRepository();

    private final List<LinguisticVariable<PolicyEntity>> linguisticVariablesList = mockRepository.findAllLinguisticVariables();
    private final List<Quantifier> quantifiersList = mockRepository.findAllQuantifiers();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        int linguisticOffsetY = 10;

        for (LinguisticVariable<PolicyEntity> linguisticVariable : linguisticVariablesList) {
            Label attributeLabel = new Label(linguisticVariable.getLinguisticVariableName());
            attributeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14");
            attributeLabel.setLayoutX(10);
            attributeLabel.setLayoutY(linguisticOffsetY);

            summarizerLabelsPane.getChildren().add(attributeLabel);
            linguisticOffsetY += 30;

            int i=0;

            for (LinguisticLabel<PolicyEntity> etiquetteLabelName : linguisticVariable.getLabels()) {

                Label etiquetteLabel = new Label(etiquetteLabelName.getLabelName());

                etiquetteLabel.setLayoutX(30);
                etiquetteLabel.setLayoutY(linguisticOffsetY + i * 25);

                summarizerLabelsPane.getChildren().addAll(etiquetteLabel);

                i++;
            }
            linguisticOffsetY += (linguisticVariablesList.size() + 1) * 11;
        }
        summarizerLabelsPane.setPrefHeight(linguisticOffsetY + 10);


        int quantifierOffsetY = 10;

        int i = 0;
        for (Quantifier quantifier : quantifiersList) {

            Label quantifierLabel = new Label(quantifier.getLabelName());
            quantifierLabel.setLayoutX(30);
            quantifierLabel.setLayoutY(quantifierOffsetY + i * 20);

            quantifierLabelsPane.getChildren().addAll(quantifierLabel);

            i++;
            quantifierOffsetY += 15;
        }
        quantifierLabelsPane.setPrefHeight((quantifierOffsetY) * 2);

        //Initialize ChoiceBoxes
        addAttributeTypeChoiceBox.getItems().addAll(SUMMARIZER, QUANTIFIER);
        editAttributeTypeChoiceBox.getItems().addAll(SUMMARIZER, QUANTIFIER);
        removeAttributeTypeChoiceBox.getItems().addAll(SUMMARIZER, QUANTIFIER);

        for (LinguisticVariable<PolicyEntity> linguisticVariable : linguisticVariablesList) {
            addAttributeNameChoiceBox.getItems().add(linguisticVariable.getLinguisticVariableName());
            editAttributeNameChoiceBox.getItems().add(linguisticVariable.getLinguisticVariableName());
            removeAttributeNameChoiceBox.getItems().add(linguisticVariable.getLinguisticVariableName());
        }

        editAttributeNameChoiceBox.setOnAction(event -> handleEtiquetteSelection());
        removeAttributeNameChoiceBox.setOnAction(event -> handleEtiquetteSelection());

        addFunctionTypeChoiceBox.getItems().addAll(TRAPEZOIDAL, TRIANGULAR, GAUSSIAN);
        editFunctionTypeChoiceBox.getItems().addAll(TRAPEZOIDAL, TRIANGULAR, GAUSSIAN);
    }

    public void handleEtiquetteSelection() {
        String selectedLinguisticVariable = editAttributeNameChoiceBox.getValue();

        switch (selectedLinguisticVariable) {
            case POLICY_TENURE -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesPT = mockRepository.findLinguisticVariableByName(POLICY_TENURE).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesPT)
                    editEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
            }
            case AGE_OF_CAR -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesAOC = mockRepository.findLinguisticVariableByName(AGE_OF_CAR).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesAOC) {
                    editEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case AGE_OF_POLICYHOLDER -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesAOP = mockRepository.findLinguisticVariableByName(AGE_OF_POLICYHOLDER).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesAOP) {
                    editEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case POPULATION_DENSITY -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesPD = mockRepository.findLinguisticVariableByName(POPULATION_DENSITY).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesPD) {
                    editEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case DISPLACEMENT -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesD = mockRepository.findLinguisticVariableByName(DISPLACEMENT).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesD) {
                    editEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case TURNING_RADIUS -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesTR = mockRepository.findLinguisticVariableByName(TURNING_RADIUS).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesTR) {
                    editEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case GROSS_WEIGHT -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesGW = mockRepository.findLinguisticVariableByName(GROSS_WEIGHT).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesGW) {
                    editEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case LENGTH -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesL = mockRepository.findLinguisticVariableByName(LENGTH).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesL) {
                    editEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case WIDTH -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesW = mockRepository.findLinguisticVariableByName(WIDTH).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesW) {
                    editEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case HEIGHT -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesH = mockRepository.findLinguisticVariableByName(HEIGHT).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesH) {
                    editEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            default -> {
            }
        }
    }

    public void addLingusticLabel(ActionEvent event) {
        if (addAttributeTypeChoiceBox.getSelectionModel().equals(SUMMARIZER)) {

            String attribute = addAttributeNameChoiceBox.getSelectionModel().toString();
            LinguisticLabel<PolicyEntity> linguisticLabel = mockRepository.findLinguisticLabelByName(attribute);
            String newLabel = addEtiquetteNameTextField.getText();
            String attributeByLabel = mockRepository.findLinguisticVariableByName(newLabel).getLinguisticVariableName();
            List<LinguisticLabel<PolicyEntity>> labels = mockRepository.findLinguisticVariableByName(attributeByLabel).getLabels();
//            labels.add(newLabel);

            LinguisticVariable<PolicyEntity> linguisticVariable = new LinguisticVariable<>(attribute, labels);

            addFunctionTypeChoiceBox.getSelectionModel();

//            mockRepository.save(linguisticVariable);
        } else if (addAttributeTypeChoiceBox.equals(QUANTIFIER)) {

        }
    }

    public void editLingusticLabel(ActionEvent event) {
    }

    public void removeLingusticLabel(ActionEvent event) {
    }
}