package p.lodz.pl.frontend.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import p.lodz.pl.backend.fuzzy.function.GaussianFunction;
import p.lodz.pl.backend.fuzzy.function.MembershipFunction;
import p.lodz.pl.backend.fuzzy.function.TrapezoidalFunction;
import p.lodz.pl.backend.fuzzy.function.TriangularFunction;
import p.lodz.pl.backend.fuzzy.function.domain.ContinuousDomain;
import p.lodz.pl.backend.fuzzy.function.domain.DiscreteDomain;
import p.lodz.pl.backend.fuzzy.function.domain.Domain;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticVariable;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;
import p.lodz.pl.backend.model.PolicyEntity;
import p.lodz.pl.backend.repository.MockRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdvancedController  {
    @FXML
    private Label addAttributeName;
    @FXML
    private Label editAttributeName;
    @FXML
    private Label removeAttributeName;
    @FXML
    private Pane editFunctionPointsPane;
    @FXML
    private Pane addFunctionPointsPane;
    @FXML
    private ChoiceBox editDomainTypeChoiceBox;
    @FXML
    private ChoiceBox addDomainTypeChoiceBox;
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
    @FXML
    private TextField addAValueTextField = new TextField();
    @FXML
    private TextField addBValueTextField = new TextField();
    @FXML
    private TextField addCValueTextField = new TextField();
    @FXML
    private TextField addDValueTextField = new TextField();
    @FXML
    private TextField addLowerBoundTextField = new TextField();
    @FXML
    private TextField addUpperBoundTextField = new TextField();
//    @FXML
//    private TextField addValuesTextField = new TextField();
    @FXML
    private TextField addDomainTextField = new TextField();

    @FXML
    private TextField editAValueTextField = new TextField();
    @FXML
    private TextField editBValueTextField = new TextField();
    @FXML
    private TextField editCValueTextField = new TextField();
    @FXML
    private TextField editDValueTextField = new TextField();
    @FXML
    private TextField editLowerBoundTextField = new TextField();
    @FXML
    private TextField editUpperBoundTextField = new TextField();
//    @FXML
//    private TextField editValuesTextField = new TextField();
    @FXML
    private TextField editDomainTextField = new TextField();

    @FXML
    private Label pointALabel = new Label("A");
    @FXML
    private Label pointBLabel = new Label("B");
    @FXML
    private Label pointCLabel = new Label("C");
    @FXML
    private Label pointDLabel = new Label("D");
    @FXML
    private Label meanLabel = new Label("Mean");
    @FXML
    private Label SDLabel = new Label("SD");
    @FXML
    private Label domainLabel = new Label("Domain");
    @FXML
    private Label upperDomainLabel = new Label("Upper domain");
    @FXML
    private Label lowerDomainLabel = new Label("Lower domain");

    private static final String CONTINUOUS_DOMAIN = "continuous";
    private static final String DISCRETE_DOMAIN = "discrete";
    private static final String SUMMARIZER = "summarizer";
    private static final String QUANTIFIER = "quantifier";
    private static final String TRAPEZOIDAL = "trapezoidal";
    private static final String TRIANGULAR = "triangular";
    private static final String GAUSSIAN = "gaussian";
    private static final String  POLICY_TENURE = "policy tenure";
    private static final String  AGE_OF_CAR = "age of car";
    private static final String  AGE_OF_POLICYHOLDER = "age of policyholder";
    private static final String  POPULATION_DENSITY = "population density";
    private static final String  DISPLACEMENT = "engine displacement";
    private static final String  TURNING_RADIUS = "turning radius";
    private static final String  GROSS_WEIGHT = "gross weight";
    private static final String  LENGTH = "length";
    private static final String  WIDTH = "width";
    private static final String  HEIGHT = "height";

    private Scene previousScene;

    private StageController stageController;

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
        this.mockRepository = stageController.getMockRepository();
        this.linguisticVariablesList = mockRepository.findAllLinguisticVariables();
        this.quantifiersList = mockRepository.findAllQuantifiers();
    }

    private MockRepository mockRepository = null;

    private List<LinguisticVariable<PolicyEntity>> linguisticVariablesList = null;
    private List<Quantifier> quantifiersList = null;

    public void setPreviousScene(Scene scene) {
        previousScene = scene;
    }

    public void goToPreviousView(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (previousScene != null) {
            stageController.setMockRepository(mockRepository);
            currentStage.setScene(previousScene);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/view.fxml"));
            Parent viewRoot = loader.load();
            Scene viewScene = new Scene(viewRoot);
            currentStage.setScene(viewScene);
        }
        currentStage.show();
    }

    public void inititalizeSummarizersAndQuantifiers() {

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

        addAttributeTypeChoiceBox.setOnAction(event -> handleAddChoiceBoxes());
        editAttributeTypeChoiceBox.setOnAction(event -> handleEditChoiceBoxes());
        removeAttributeTypeChoiceBox.setOnAction(event -> handleRemoveChoiceBoxes());

        editAttributeNameChoiceBox.setOnAction(event -> handleEditEtiquetteSelection());
        removeAttributeNameChoiceBox.setOnAction(event -> handleRemoveEtiquetteSelection());

        addFunctionTypeChoiceBox.getItems().addAll(TRAPEZOIDAL, TRIANGULAR, GAUSSIAN);
        editFunctionTypeChoiceBox.getItems().addAll(TRAPEZOIDAL, TRIANGULAR, GAUSSIAN);

        addFunctionTypeChoiceBox.setOnAction(event -> handleAddFunctionSelection());
        editFunctionTypeChoiceBox.setOnAction(event -> handleEditFunctionSelection());

        addDomainTypeChoiceBox.getItems().addAll(CONTINUOUS_DOMAIN, DISCRETE_DOMAIN);
        editDomainTypeChoiceBox.getItems().addAll(CONTINUOUS_DOMAIN, DISCRETE_DOMAIN);
    }

    public void handleAddChoiceBoxes() {
//        if (addAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(SUMMARIZER) ||
//                editAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(SUMMARIZER) ||
//                removeAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(SUMMARIZER)) {
//            for (LinguisticVariable<PolicyEntity> linguisticVariable : linguisticVariablesList) {
//                addAttributeNameChoiceBox.getItems().add(linguisticVariable.getLinguisticVariableName());
//                editAttributeNameChoiceBox.getItems().add(linguisticVariable.getLinguisticVariableName());
//                removeAttributeNameChoiceBox.getItems().add(linguisticVariable.getLinguisticVariableName());
//            }
//        } else if (addAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUANTIFIER) ||
//                editAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUANTIFIER) ||
//                removeAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUANTIFIER)) {
//            for (Quantifier quantifier : quantifiersList) {
//                addAttributeNameChoiceBox.getItems().add(quantifier.getLinguisticVariableName());
//                editAttributeNameChoiceBox.getItems().add(quantifier.getLinguisticVariableName());
//                removeAttributeNameChoiceBox.getItems().add(quantifier.getLinguisticVariableName());
//            }
//        }

        addAttributeNameChoiceBox.getItems().clear();
        if (addAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(SUMMARIZER)) {
//            if (addAttributeNameChoiceBox.isShowing()) {
                for (LinguisticVariable<PolicyEntity> linguisticVariable : linguisticVariablesList) {
                    addAttributeNameChoiceBox.getItems().add(linguisticVariable.getLinguisticVariableName());
                }
//            } else if (!addAttributeNameChoiceBox.isShowing()){
//                addAnchorPane.getChildren().add(addAttributeNameChoiceBox);
//                for (LinguisticVariable<PolicyEntity> linguisticVariable : linguisticVariablesList) {
//                    addAttributeNameChoiceBox.getItems().add(linguisticVariable.getLinguisticVariableName());
//                }
//            }
        }
        else if (addAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUANTIFIER)) {
            addAnchorPane.getChildren().remove(addAttributeNameChoiceBox);
            addAnchorPane.getChildren().remove(addAttributeName);
        }
    }

    public void handleEditChoiceBoxes() {
        editEtiquetteNameChoiceBox.getItems().clear();
        if (editAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(SUMMARIZER)) {
            for (LinguisticVariable<PolicyEntity> linguisticVariable : linguisticVariablesList) {
                editAttributeNameChoiceBox.getItems().add(linguisticVariable.getLinguisticVariableName());
            }
        } else if (editAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUANTIFIER)) {
            editAnchorPane.getChildren().remove(editAttributeNameChoiceBox);
            editAnchorPane.getChildren().remove(editAttributeName);
            for (Quantifier quantifier : quantifiersList) {
                editEtiquetteNameChoiceBox.getItems().add(quantifier.getLabelName());
            }
        }
    }

    public void handleRemoveChoiceBoxes() {
        removeEtiquetteNameChoiceBox.getItems().clear();
        if (removeAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(SUMMARIZER)) {
            for (LinguisticVariable<PolicyEntity> linguisticVariable : linguisticVariablesList) {
                removeAttributeNameChoiceBox.getItems().add(linguisticVariable.getLinguisticVariableName());
            }
        } else if (removeAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUANTIFIER)) {
            removeAnchorPane.getChildren().remove(removeAttributeNameChoiceBox);
            removeAnchorPane.getChildren().remove(removeAttributeName);
            for (Quantifier quantifier : quantifiersList) {
                removeEtiquetteNameChoiceBox.getItems().add(quantifier.getLabelName());
            }
        }
    }

    public void handleEditEtiquetteSelection() {
        String selectedLinguisticVariable = editAttributeNameChoiceBox.getValue();
        editEtiquetteNameChoiceBox.getItems().clear();

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

    public void handleRemoveEtiquetteSelection() {
        String selectedLinguisticVariable = removeAttributeNameChoiceBox.getValue();
        removeEtiquetteNameChoiceBox.getItems().clear();

        switch (selectedLinguisticVariable) {
            case POLICY_TENURE -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesPT = mockRepository.findLinguisticVariableByName(POLICY_TENURE).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesPT)
                    removeEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
            }
            case AGE_OF_CAR -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesAOC = mockRepository.findLinguisticVariableByName(AGE_OF_CAR).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesAOC) {
                    removeEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case AGE_OF_POLICYHOLDER -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesAOP = mockRepository.findLinguisticVariableByName(AGE_OF_POLICYHOLDER).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesAOP) {
                    removeEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case POPULATION_DENSITY -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesPD = mockRepository.findLinguisticVariableByName(POPULATION_DENSITY).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesPD) {
                    removeEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case DISPLACEMENT -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesD = mockRepository.findLinguisticVariableByName(DISPLACEMENT).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesD) {
                    removeEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case TURNING_RADIUS -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesTR = mockRepository.findLinguisticVariableByName(TURNING_RADIUS).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesTR) {
                    removeEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case GROSS_WEIGHT -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesGW = mockRepository.findLinguisticVariableByName(GROSS_WEIGHT).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesGW) {
                    removeEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case LENGTH -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesL = mockRepository.findLinguisticVariableByName(LENGTH).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesL) {
                    removeEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case WIDTH -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesW = mockRepository.findLinguisticVariableByName(WIDTH).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesW) {
                    removeEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            case HEIGHT -> {
                List<LinguisticLabel<PolicyEntity>> etiquettesH = mockRepository.findLinguisticVariableByName(HEIGHT).getLabels();
                for (LinguisticLabel<PolicyEntity> e : etiquettesH) {
                    removeEtiquetteNameChoiceBox.getItems().add(e.getLabelName());
                }
            }
            default -> {
            }
        }
    }

    public void addLingusticLabel(ActionEvent event) {
        if (addAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(SUMMARIZER)) {

            String variableName = addAttributeNameChoiceBox.getSelectionModel().getSelectedItem();
            LinguisticVariable<PolicyEntity> variableByName = mockRepository.findLinguisticVariableByName(variableName);
            String newLabelName = addEtiquetteNameTextField.getText();

            MembershipFunction function = getAddFunction(addFunctionTypeChoiceBox.getSelectionModel().getSelectedItem());

            LinguisticLabel<PolicyEntity> label = new LinguisticLabel<>(variableName,
                    newLabelName,
                    variableByName.getLabels().get(0).getExtractor(),
                    function);

            variableByName.addLabel(label);
            mockRepository.save(variableByName);
            stageController.setMockRepository(mockRepository);

        } else if (addAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUANTIFIER)) {

//            String variableName = addAttributeNameChoiceBox.getSelectionModel().getSelectedItem();
//            LinguisticVariable<PolicyEntity> variableByName = mockRepository.findLinguisticVariableByName(variableName);
//            String newLabelName = addEtiquetteNameTextField.getText();
//            System.out.println(newLabelName);
//            MembershipFunction function = getAddFunction(addFunctionTypeChoiceBox.getSelectionModel().getSelectedItem());
//
//            LinguisticLabel<PolicyEntity> label = new LinguisticLabel<>(variableName,
//                    newLabelName,
//                    variableByName.getLabels().get(0).getExtractor(),
//                    function);
//
//            variableByName.addLabel(label);
//            mockRepository.save(variableByName);
//            stageController.setMockRepository(mockRepository);
        }
    }

    public void editLingusticLabel(ActionEvent event) {
        if (editAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(SUMMARIZER)) {

        } else if (editAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUANTIFIER)) {

        }
    }

    public void removeLingusticLabel(ActionEvent event) {
//        if (removeAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(SUMMARIZER)) {
//            String selectedItem = removeEtiquetteNameChoiceBox.getSelectionModel().getSelectedItem().toString();
//
//            mockRepository.deleteByLabelName(selectedItem);
//
//        } else if (removeAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUANTIFIER)) {
//            String selectedItem = removeEtiquetteNameChoiceBox.getSelectionModel().getSelectedItem().toString();
//
//            mockRepository.deleteByLabelName(selectedItem);
//        }
        String selectedItem = removeEtiquetteNameChoiceBox.getSelectionModel().getSelectedItem().toString();

        mockRepository.deleteByLabelName(selectedItem);
    }

    public void handleAddFunctionSelection() {
        String selectedFunction = addFunctionTypeChoiceBox.getValue();

        addFunctionPointsPane.getChildren().clear();

        switch (selectedFunction) {
            case TRAPEZOIDAL -> {

                pointALabel.setLayoutX(5);
                pointALabel.setLayoutY(5);
                pointBLabel.setLayoutX(5);
                pointBLabel.setLayoutY(50);
                pointCLabel.setLayoutX(105);
                pointCLabel.setLayoutY(5);
                pointDLabel.setLayoutX(105);
                pointDLabel.setLayoutY(50);

                addAValueTextField.setLayoutX(25);
                addAValueTextField.setLayoutY(5);
                addAValueTextField.setStyle("-fx-max-width: 60");
                addBValueTextField.setLayoutX(25);
                addBValueTextField.setLayoutY(50);
                addBValueTextField.setStyle("-fx-max-width: 60");
                addCValueTextField.setLayoutX(125);
                addCValueTextField.setLayoutY(5);
                addCValueTextField.setStyle("-fx-max-width: 60");
                addDValueTextField.setLayoutX(125);
                addDValueTextField.setLayoutY(50);
                addDValueTextField.setStyle("-fx-max-width: 60");

                if (addDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(DISCRETE_DOMAIN)) {
                    domainLabel.setLayoutX(220);
                    domainLabel.setLayoutY(5);

                    addDomainTextField.setLayoutX(205);
                    addDomainTextField.setLayoutY(30);
                    addDomainTextField.setStyle("-fx-max-width: 70");

                    addFunctionPointsPane.getChildren().addAll(domainLabel, addDomainTextField);

                } else if (addDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(CONTINUOUS_DOMAIN)) {
                    lowerDomainLabel.setLayoutX(205);
                    lowerDomainLabel.setLayoutY(5);
                    upperDomainLabel.setLayoutX(205);
                    upperDomainLabel.setLayoutY(50);

                    addLowerBoundTextField.setLayoutX(205);
                    addLowerBoundTextField.setLayoutY(25);
                    addLowerBoundTextField.setStyle("-fx-max-width: 70");
                    addUpperBoundTextField.setLayoutX(205);
                    addUpperBoundTextField.setLayoutY(70);
                    addUpperBoundTextField.setStyle("-fx-max-width: 70");

                    addFunctionPointsPane.getChildren().addAll(lowerDomainLabel, upperDomainLabel, addLowerBoundTextField, addUpperBoundTextField);
                }

                addFunctionPointsPane.getChildren().addAll(pointALabel, pointBLabel, pointCLabel, pointDLabel, addAValueTextField, addBValueTextField, addCValueTextField, addDValueTextField);
            }
            case TRIANGULAR -> {

                pointALabel.setLayoutX(5);
                pointALabel.setLayoutY(5);
                pointBLabel.setLayoutX(5);
                pointBLabel.setLayoutY(50);
                pointCLabel.setLayoutX(105);
                pointCLabel.setLayoutY(5);

                addAValueTextField.setLayoutX(25);
                addAValueTextField.setLayoutY(5);
                addAValueTextField.setStyle("-fx-max-width: 60");
                addBValueTextField.setLayoutX(25);
                addBValueTextField.setLayoutY(50);
                addBValueTextField.setStyle("-fx-max-width: 60");
                addCValueTextField.setLayoutX(125);
                addCValueTextField.setLayoutY(5);
                addCValueTextField.setStyle("-fx-max-width: 60");

                if (addDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(DISCRETE_DOMAIN)) {
                    domainLabel.setLayoutX(220);
                    domainLabel.setLayoutY(5);

                    addDomainTextField.setLayoutX(205);
                    addDomainTextField.setLayoutY(30);
                    addDomainTextField.setStyle("-fx-max-width: 70");

                    addFunctionPointsPane.getChildren().addAll(domainLabel, addDomainTextField);

                } else if (addDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(CONTINUOUS_DOMAIN)) {
                    lowerDomainLabel.setLayoutX(205);
                    lowerDomainLabel.setLayoutY(5);
                    upperDomainLabel.setLayoutX(205);
                    upperDomainLabel.setLayoutY(50);

                    addLowerBoundTextField.setLayoutX(205);
                    addLowerBoundTextField.setLayoutY(25);
                    addLowerBoundTextField.setStyle("-fx-max-width: 70");
                    addUpperBoundTextField.setLayoutX(205);
                    addUpperBoundTextField.setLayoutY(70);
                    addUpperBoundTextField.setStyle("-fx-max-width: 70");

                    addFunctionPointsPane.getChildren().addAll(lowerDomainLabel, upperDomainLabel, addLowerBoundTextField, addUpperBoundTextField);
                }

                addFunctionPointsPane.getChildren().addAll(pointALabel, pointBLabel, pointCLabel, addAValueTextField, addBValueTextField, addCValueTextField);
            }
            case GAUSSIAN -> {

                meanLabel.setLayoutX(5);
                meanLabel.setLayoutY(5);
                SDLabel.setLayoutX(5);
                SDLabel.setLayoutY(50);

                addAValueTextField.setLayoutX(40);
                addAValueTextField.setLayoutY(5);
                addAValueTextField.setStyle("-fx-max-width: 60");
                addBValueTextField.setLayoutX(40);
                addBValueTextField.setLayoutY(50);
                addBValueTextField.setStyle("-fx-max-width: 60");

                if (addDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(DISCRETE_DOMAIN)) {
                    domainLabel.setLayoutX(220);
                    domainLabel.setLayoutY(5);

                    addDomainTextField.setLayoutX(205);
                    addDomainTextField.setLayoutY(30);
                    addDomainTextField.setStyle("-fx-max-width: 70");

                    addFunctionPointsPane.getChildren().addAll(domainLabel, addDomainTextField);

                } else if (addDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(CONTINUOUS_DOMAIN)) {
                    lowerDomainLabel.setLayoutX(205);
                    lowerDomainLabel.setLayoutY(5);
                    upperDomainLabel.setLayoutX(205);
                    upperDomainLabel.setLayoutY(50);

                    addLowerBoundTextField.setLayoutX(205);
                    addLowerBoundTextField.setLayoutY(25);
                    addLowerBoundTextField.setStyle("-fx-max-width: 70");
                    addUpperBoundTextField.setLayoutX(205);
                    addUpperBoundTextField.setLayoutY(70);
                    addUpperBoundTextField.setStyle("-fx-max-width: 70");

                    addFunctionPointsPane.getChildren().addAll(lowerDomainLabel, upperDomainLabel, addLowerBoundTextField, addUpperBoundTextField);
                }

                addFunctionPointsPane.getChildren().addAll(meanLabel, SDLabel, addAValueTextField, addBValueTextField);
            }
        }
    }

    private void handleEditFunctionSelection() {
        String selectedFunction = editFunctionTypeChoiceBox.getValue();

        editFunctionPointsPane.getChildren().clear();

        switch (selectedFunction) {
            case TRAPEZOIDAL -> {

                pointALabel.setLayoutX(5);
                pointALabel.setLayoutY(5);
                pointBLabel.setLayoutX(5);
                pointBLabel.setLayoutY(50);
                pointCLabel.setLayoutX(105);
                pointCLabel.setLayoutY(5);
                pointDLabel.setLayoutX(105);
                pointDLabel.setLayoutY(50);

                editAValueTextField.setLayoutX(25);
                editAValueTextField.setLayoutY(5);
                editAValueTextField.setStyle("-fx-max-width: 60");
                editBValueTextField.setLayoutX(25);
                editBValueTextField.setLayoutY(50);
                editBValueTextField.setStyle("-fx-max-width: 60");
                editCValueTextField.setLayoutX(125);
                editCValueTextField.setLayoutY(5);
                editCValueTextField.setStyle("-fx-max-width: 60");
                editDValueTextField.setLayoutX(125);
                editDValueTextField.setLayoutY(50);
                editDValueTextField.setStyle("-fx-max-width: 60");

                if (editDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(DISCRETE_DOMAIN)) {
                    domainLabel.setLayoutX(220);
                    domainLabel.setLayoutY(5);

                    editDomainTextField.setLayoutX(205);
                    editDomainTextField.setLayoutY(30);
                    editDomainTextField.setStyle("-fx-max-width: 70");

                    editFunctionPointsPane.getChildren().addAll(domainLabel, editDomainTextField);

                } else if (editDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(CONTINUOUS_DOMAIN)) {
                    lowerDomainLabel.setLayoutX(205);
                    lowerDomainLabel.setLayoutY(5);
                    upperDomainLabel.setLayoutX(205);
                    upperDomainLabel.setLayoutY(50);

                    editLowerBoundTextField.setLayoutX(205);
                    editLowerBoundTextField.setLayoutY(25);
                    editLowerBoundTextField.setStyle("-fx-max-width: 70");
                    editUpperBoundTextField.setLayoutX(205);
                    editUpperBoundTextField.setLayoutY(70);
                    editUpperBoundTextField.setStyle("-fx-max-width: 70");

                    editFunctionPointsPane.getChildren().addAll(lowerDomainLabel, upperDomainLabel, editLowerBoundTextField, editUpperBoundTextField);
                }

                editFunctionPointsPane.getChildren().addAll(pointALabel, pointBLabel, pointCLabel, pointDLabel, editAValueTextField, editBValueTextField, editCValueTextField, editDValueTextField);
            }
            case TRIANGULAR -> {

                pointALabel.setLayoutX(5);
                pointALabel.setLayoutY(5);
                pointBLabel.setLayoutX(5);
                pointBLabel.setLayoutY(50);
                pointCLabel.setLayoutX(105);
                pointCLabel.setLayoutY(5);

                editAValueTextField.setLayoutX(25);
                editAValueTextField.setLayoutY(5);
                editAValueTextField.setStyle("-fx-max-width: 60");
                editBValueTextField.setLayoutX(25);
                editBValueTextField.setLayoutY(50);
                editBValueTextField.setStyle("-fx-max-width: 60");
                editCValueTextField.setLayoutX(125);
                editCValueTextField.setLayoutY(5);
                editCValueTextField.setStyle("-fx-max-width: 60");

                if (editDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(DISCRETE_DOMAIN)) {
                    domainLabel.setLayoutX(220);
                    domainLabel.setLayoutY(5);

                    editDomainTextField.setLayoutX(205);
                    editDomainTextField.setLayoutY(30);
                    editDomainTextField.setStyle("-fx-max-width: 70");

                    editFunctionPointsPane.getChildren().addAll(domainLabel, editDomainTextField);

                } else if (editDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(CONTINUOUS_DOMAIN)) {
                    lowerDomainLabel.setLayoutX(205);
                    lowerDomainLabel.setLayoutY(5);
                    upperDomainLabel.setLayoutX(205);
                    upperDomainLabel.setLayoutY(50);

                    editLowerBoundTextField.setLayoutX(205);
                    editLowerBoundTextField.setLayoutY(25);
                    editLowerBoundTextField.setStyle("-fx-max-width: 70");
                    editUpperBoundTextField.setLayoutX(205);
                    editUpperBoundTextField.setLayoutY(70);
                    editUpperBoundTextField.setStyle("-fx-max-width: 70");

                    editFunctionPointsPane.getChildren().addAll(lowerDomainLabel, upperDomainLabel, editLowerBoundTextField, editUpperBoundTextField);
                }

                editFunctionPointsPane.getChildren().addAll(pointALabel, pointBLabel, pointCLabel, editAValueTextField, editBValueTextField, editCValueTextField);
            }
            case GAUSSIAN -> {

                meanLabel.setLayoutX(5);
                meanLabel.setLayoutY(5);
                SDLabel.setLayoutX(5);
                SDLabel.setLayoutY(50);

                editAValueTextField.setLayoutX(40);
                editAValueTextField.setLayoutY(5);
                editAValueTextField.setStyle("-fx-max-width: 60");
                editBValueTextField.setLayoutX(40);
                editBValueTextField.setLayoutY(50);
                editBValueTextField.setStyle("-fx-max-width: 60");

                if (editDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(DISCRETE_DOMAIN)) {
                    domainLabel.setLayoutX(220);
                    domainLabel.setLayoutY(5);

                    editDomainTextField.setLayoutX(205);
                    editDomainTextField.setLayoutY(30);
                    editDomainTextField.setStyle("-fx-max-width: 70");

                    editFunctionPointsPane.getChildren().addAll(domainLabel, editDomainTextField);

                } else if (editDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(CONTINUOUS_DOMAIN)) {
                    lowerDomainLabel.setLayoutX(205);
                    lowerDomainLabel.setLayoutY(5);
                    upperDomainLabel.setLayoutX(205);
                    upperDomainLabel.setLayoutY(50);

                    editLowerBoundTextField.setLayoutX(205);
                    editLowerBoundTextField.setLayoutY(25);
                    editLowerBoundTextField.setStyle("-fx-max-width: 70");
                    editUpperBoundTextField.setLayoutX(205);
                    editUpperBoundTextField.setLayoutY(70);
                    editUpperBoundTextField.setStyle("-fx-max-width: 70");

                    editFunctionPointsPane.getChildren().addAll(lowerDomainLabel, upperDomainLabel, editLowerBoundTextField, editUpperBoundTextField);
                }

                editFunctionPointsPane.getChildren().addAll(meanLabel, SDLabel, editAValueTextField, editBValueTextField);
            }
        }
    }

    private MembershipFunction getAddFunction(String name) {
        Domain domain;
        if (addDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(CONTINUOUS_DOMAIN)) {
            domain = getAddDomain(CONTINUOUS_DOMAIN);
        } else {
            domain = getAddDomain(DISCRETE_DOMAIN);
        }
        switch (name) {
            case TRAPEZOIDAL -> {
                double a = Double.parseDouble(addAValueTextField.getText());
                double b = Double.parseDouble(addBValueTextField.getText());
                double c = Double.parseDouble(addCValueTextField.getText());
                double d = Double.parseDouble(addDValueTextField.getText());
                return new TrapezoidalFunction(domain, a, b, c, d);
            }
            case TRIANGULAR -> {
                double a = Double.parseDouble(addAValueTextField.getText());
                double b = Double.parseDouble(addBValueTextField.getText());
                double c = Double.parseDouble(addCValueTextField.getText());
                return new TriangularFunction(domain, a, b, c);
            }
            case GAUSSIAN -> {
                double a = Double.parseDouble(addAValueTextField.getText());
                double b = Double.parseDouble(addBValueTextField.getText());
                return new GaussianFunction(domain, a, b);
            }
            default -> {
                return null;
            }
        }
    }

    private MembershipFunction getEditFunction(String name) {
        Domain domain;
        if (editDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(CONTINUOUS_DOMAIN)) {
            domain = getAddDomain(CONTINUOUS_DOMAIN);
        } else {
            domain = getAddDomain(DISCRETE_DOMAIN);
        }
        switch (name) {
            case TRAPEZOIDAL -> {
                double a = Double.parseDouble(editAValueTextField.getText());
                double b = Double.parseDouble(editBValueTextField.getText());
                double c = Double.parseDouble(editCValueTextField.getText());
                double d = Double.parseDouble(editDValueTextField.getText());
                return new TrapezoidalFunction(domain, a, b, c, d);
            }
            case TRIANGULAR -> {
                double a = Double.parseDouble(editAValueTextField.getText());
                double b = Double.parseDouble(editBValueTextField.getText());
                double c = Double.parseDouble(editCValueTextField.getText());
                return new TriangularFunction(domain, a, b, c);
            }
            case GAUSSIAN -> {
                double a = Double.parseDouble(editAValueTextField.getText());
                double b = Double.parseDouble(editBValueTextField.getText());
                return new GaussianFunction(domain, a, b);
            }
            default -> {
                return null;
            }
        }
    }

    private Domain getAddDomain(String domainName) {
        if (CONTINUOUS_DOMAIN.equals(domainName)) {
            double lowerBound = Double.parseDouble(addLowerBoundTextField.getText());
            double upperBound = Double.parseDouble(addUpperBoundTextField.getText());
            return new ContinuousDomain(lowerBound, upperBound);
        } else if (DISCRETE_DOMAIN.equals(domainName)) {
            List<Double> values = new ArrayList<>();
            String[] split = addDomainTextField.getText().split(",");
            for (String s : split) {
                values.add(Double.parseDouble(s));
            }
            return new DiscreteDomain(values);
        } else {
            return null;
        }
    }

    private Domain getEditDomain(String domainName) {
        if (CONTINUOUS_DOMAIN.equals(domainName)) {
            double lowerBound = Double.parseDouble(editLowerBoundTextField.getText());
            double upperBound = Double.parseDouble(editUpperBoundTextField.getText());
            return new ContinuousDomain(lowerBound, upperBound);
        } else if (DISCRETE_DOMAIN.equals(domainName)) {
            List<Double> values = new ArrayList<>();
            String[] split = editDomainTextField.getText().split(",");
            for (String s : split) {
                values.add(Double.parseDouble(s));
            }
            return new DiscreteDomain(values);
        } else {
            return null;
        }
    }
}