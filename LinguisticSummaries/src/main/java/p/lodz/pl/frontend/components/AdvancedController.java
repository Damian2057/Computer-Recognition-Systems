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
import p.lodz.pl.backend.fuzzy.function.domain.DiscreteDomain;
import p.lodz.pl.backend.fuzzy.function.domain.Domain;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticVariable;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;
import p.lodz.pl.backend.fuzzy.set.FuzzySet;
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
    private ChoiceBox<String> editDomainTypeChoiceBox;
    @FXML
    private ChoiceBox<String> addDomainTypeChoiceBox;
    @FXML
    private Button goBackButton;
    @FXML
    private AnchorPane qualifierLabelsPane;
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
    private Label StandardDeviationLabel = new Label("SD");
    @FXML
    private Label domainLabel = new Label("Domain");
    @FXML
    private Label upperDomainLabel = new Label("Upper domain");
    @FXML
    private Label lowerDomainLabel = new Label("Lower domain");

    private Label quantifierTypeLabel = new Label("Quantifier type");

    private ChoiceBox<String> isAbsoluteChoiceBox = new ChoiceBox<>();

    private static final String ABSOLUTE = "absolute";
    private static final String RELATIVE = "relative";
    private static final String CONTINUOUS_DOMAIN = "continuous";
    private static final String DISCRETE_DOMAIN = "discrete";
    private static final String QUALIFIER = "qualifier";
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
    private static final String STYLE = "-fx-max-width: 70";
    private static final String POINTS_STYLE = "-fx-max-width: 60";

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

    public void inititalizeQualifiersAndQuantifiers() {

        int linguisticOffsetY = 10;

        for (LinguisticVariable<PolicyEntity> linguisticVariable : linguisticVariablesList) {
            Label attributeLabel = new Label(linguisticVariable.getLinguisticVariableName());
            attributeLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14");
            attributeLabel.setLayoutX(10);
            attributeLabel.setLayoutY(linguisticOffsetY);

            qualifierLabelsPane.getChildren().add(attributeLabel);
            linguisticOffsetY += 30;

            int i=0;

            for (LinguisticLabel<PolicyEntity> etiquetteLabelName : linguisticVariable.getLabels()) {

                Label etiquetteLabel = new Label(etiquetteLabelName.getLabelName());

                etiquetteLabel.setLayoutX(30);
                etiquetteLabel.setLayoutY(linguisticOffsetY + i * 25);

                qualifierLabelsPane.getChildren().addAll(etiquetteLabel);

                i++;
            }
            linguisticOffsetY += (linguisticVariablesList.size() + 1) * 11;
        }
        qualifierLabelsPane.setPrefHeight(linguisticOffsetY + 10);


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
        quantifierLabelsPane.setPrefHeight((quantifierOffsetY) * 2 + 30);

        //Initialize ChoiceBoxes
        addAttributeTypeChoiceBox.getItems().addAll(QUALIFIER, QUANTIFIER);
        editAttributeTypeChoiceBox.getItems().addAll(QUALIFIER, QUANTIFIER);

        addAttributeTypeChoiceBox.setOnAction(event -> handleAddChoiceBoxes());
        editAttributeTypeChoiceBox.setOnAction(event -> handleEditChoiceBoxes());

        editAttributeNameChoiceBox.setOnAction(event -> handleEditEtiquetteSelection());

        addFunctionTypeChoiceBox.getItems().addAll(TRAPEZOIDAL, TRIANGULAR, GAUSSIAN);
        editFunctionTypeChoiceBox.getItems().addAll(TRAPEZOIDAL, TRIANGULAR, GAUSSIAN);

        addFunctionTypeChoiceBox.setOnAction(event -> handleAddFunctionSelection());
        editFunctionTypeChoiceBox.setOnAction(event -> handleEditFunctionSelection());

        addDomainTypeChoiceBox.getItems().addAll(CONTINUOUS_DOMAIN, DISCRETE_DOMAIN);
        editDomainTypeChoiceBox.getItems().addAll(CONTINUOUS_DOMAIN, DISCRETE_DOMAIN);
    }

    public void handleAddChoiceBoxes() {
        addAttributeNameChoiceBox.getItems().clear();
        if (addAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUALIFIER)) {
                for (LinguisticVariable<PolicyEntity> linguisticVariable : linguisticVariablesList) {
                    addAttributeNameChoiceBox.getItems().add(linguisticVariable.getLinguisticVariableName());
                }
        }
        else if (addAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUANTIFIER)) {
            addAnchorPane.getChildren().remove(addAttributeNameChoiceBox);
            addAnchorPane.getChildren().remove(addAttributeName);

            quantifierTypeLabel.setLayoutX(5);
            quantifierTypeLabel.setLayoutY(105);
            isAbsoluteChoiceBox.getItems().addAll(ABSOLUTE, RELATIVE);
            isAbsoluteChoiceBox.setLayoutX(115);
            isAbsoluteChoiceBox.setLayoutY(105);

            addAnchorPane.getChildren().removeAll(quantifierTypeLabel, isAbsoluteChoiceBox);
            addAnchorPane.getChildren().addAll(quantifierTypeLabel, isAbsoluteChoiceBox);
        }
    }

    public void handleEditChoiceBoxes() {
        editEtiquetteNameChoiceBox.getItems().clear();
        if (editAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUALIFIER)) {
            for (LinguisticVariable<PolicyEntity> linguisticVariable : linguisticVariablesList) {
                editAttributeNameChoiceBox.getItems().add(linguisticVariable.getLinguisticVariableName());
            }
        } else if (editAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUANTIFIER)) {
            editAnchorPane.getChildren().remove(editAttributeNameChoiceBox);
            editAnchorPane.getChildren().remove(editAttributeName);
            quantifierTypeLabel.setLayoutX(5);
            quantifierTypeLabel.setLayoutY(105);
            isAbsoluteChoiceBox.getItems().addAll(ABSOLUTE, RELATIVE);
            isAbsoluteChoiceBox.setLayoutX(115);
            isAbsoluteChoiceBox.setLayoutY(105);
            editAnchorPane.getChildren().addAll(quantifierTypeLabel, isAbsoluteChoiceBox);
            for (Quantifier quantifier : quantifiersList) {
                editEtiquetteNameChoiceBox.getItems().add(quantifier.getLabelName());
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
            default -> throw new UnsupportedOperationException("There is no such linguistic variable");
        }
    }

    public void addLingusticLabel() {
        if (addAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUALIFIER)) {

            String variableName = addAttributeNameChoiceBox.getSelectionModel().getSelectedItem();
            LinguisticVariable<PolicyEntity> variableByName = mockRepository.findLinguisticVariableByName(variableName);
            String newLabelName = addEtiquetteNameTextField.getText();

            MembershipFunction function = getAddFunction(variableByName.getLabels().get(0), addFunctionTypeChoiceBox.getSelectionModel().getSelectedItem());

            LinguisticLabel<PolicyEntity> label = new LinguisticLabel<>(variableName,
                    newLabelName,
                    variableByName.getLabels().get(0).getExtractor(),
                    function);

            variableByName.addLabel(label);
            mockRepository.save(variableByName);
            stageController.setMockRepository(mockRepository);

        } else if (addAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUANTIFIER)) {

            String newLabelName = addEtiquetteNameTextField.getText();

            boolean isAbsolute =  isAbsoluteChoiceBox.getSelectionModel().getSelectedItem().equals(ABSOLUTE);
            Quantifier quantifier;
            if (isAbsolute) {
                quantifier = mockRepository.findQuantifierByName("Less than 9000");
            } else {
                quantifier = mockRepository.findQuantifierByName("Almost none");
            }
            MembershipFunction function = getAddFunction(quantifier.getFuzzySet(), addFunctionTypeChoiceBox.getSelectionModel().getSelectedItem());
            Quantifier newQuantifier = new Quantifier(newLabelName, x -> x, function, isAbsolute);

            mockRepository.save(newQuantifier);
            stageController.setMockRepository(mockRepository);
        }
    }

    public void editLingusticLabel() {
        if (editAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUALIFIER)) {

            String selectedQualifier = editAttributeNameChoiceBox.getSelectionModel().getSelectedItem();
            String selectedEtiquette = editEtiquetteNameChoiceBox.getSelectionModel().getSelectedItem();
            LinguisticVariable<PolicyEntity> linguisticVariable = mockRepository.findLinguisticVariableByName(selectedQualifier);

            MembershipFunction function = getEditFunction(linguisticVariable.getLabels().get(0), editFunctionTypeChoiceBox.getSelectionModel().getSelectedItem());

            LinguisticLabel<PolicyEntity> label = new LinguisticLabel<>(selectedQualifier,
                    selectedEtiquette,
                    linguisticVariable.getLabels().get(0).getExtractor(),
                    function);

            List<LinguisticLabel<PolicyEntity>> labels = linguisticVariable.getLabels();
            int index = labels.indexOf(mockRepository.findLinguisticLabelByName(selectedEtiquette));
            labels.set(index, label);
            linguisticVariable.setLabels(labels);
            mockRepository.save(linguisticVariable);
            stageController.setMockRepository(mockRepository);

        } else if (editAttributeTypeChoiceBox.getSelectionModel().getSelectedItem().equals(QUANTIFIER)) {
            String selectedQuantifier =  editEtiquetteNameChoiceBox.getSelectionModel().getSelectedItem();

            Quantifier oldQuantifier = mockRepository.findQuantifierByName(selectedQuantifier);
            mockRepository.delete(oldQuantifier);
            boolean isAbsolute =  isAbsoluteChoiceBox.getSelectionModel().getSelectedItem().equals(ABSOLUTE);
            Quantifier quantifier;
            if (isAbsolute) {
                quantifier = mockRepository.findQuantifierByName("Less than 9000");
            } else {
                quantifier = mockRepository.findQuantifierByName("Almost none");
            }

            MembershipFunction function = getEditFunction(quantifier.getFuzzySet(), editFunctionTypeChoiceBox.getSelectionModel().getSelectedItem());

            Quantifier newQuantifier = new Quantifier(selectedQuantifier, x -> x, function, isAbsolute);

            mockRepository.save(newQuantifier);
            stageController.setMockRepository(mockRepository);
        }
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
                addAValueTextField.setStyle(POINTS_STYLE);
                addBValueTextField.setLayoutX(25);
                addBValueTextField.setLayoutY(50);
                addBValueTextField.setStyle(POINTS_STYLE);
                addCValueTextField.setLayoutX(125);
                addCValueTextField.setLayoutY(5);
                addCValueTextField.setStyle(POINTS_STYLE);
                addDValueTextField.setLayoutX(125);
                addDValueTextField.setLayoutY(50);
                addDValueTextField.setStyle(POINTS_STYLE);

                if (addDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(DISCRETE_DOMAIN)) {
                    domainLabel.setLayoutX(220);
                    domainLabel.setLayoutY(5);

                    addDomainTextField.setLayoutX(205);
                    addDomainTextField.setLayoutY(30);
                    addDomainTextField.setStyle(STYLE);

                    addFunctionPointsPane.getChildren().addAll(domainLabel, addDomainTextField);

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
                addAValueTextField.setStyle(POINTS_STYLE);
                addBValueTextField.setLayoutX(25);
                addBValueTextField.setLayoutY(50);
                addBValueTextField.setStyle(POINTS_STYLE);
                addCValueTextField.setLayoutX(125);
                addCValueTextField.setLayoutY(5);
                addCValueTextField.setStyle(POINTS_STYLE);

                if (addDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(DISCRETE_DOMAIN)) {
                    domainLabel.setLayoutX(220);
                    domainLabel.setLayoutY(5);

                    addDomainTextField.setLayoutX(205);
                    addDomainTextField.setLayoutY(30);
                    addDomainTextField.setStyle(STYLE);

                    addFunctionPointsPane.getChildren().addAll(domainLabel, addDomainTextField);

                }

                addFunctionPointsPane.getChildren().addAll(pointALabel, pointBLabel, pointCLabel, addAValueTextField, addBValueTextField, addCValueTextField);
            }
            case GAUSSIAN -> {

                meanLabel.setLayoutX(5);
                meanLabel.setLayoutY(5);
                StandardDeviationLabel.setLayoutX(5);
                StandardDeviationLabel.setLayoutY(50);

                addAValueTextField.setLayoutX(40);
                addAValueTextField.setLayoutY(5);
                addAValueTextField.setStyle(POINTS_STYLE);
                addBValueTextField.setLayoutX(40);
                addBValueTextField.setLayoutY(50);
                addBValueTextField.setStyle(POINTS_STYLE);

                if (addDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(DISCRETE_DOMAIN)) {
                    domainLabel.setLayoutX(220);
                    domainLabel.setLayoutY(5);

                    addDomainTextField.setLayoutX(205);
                    addDomainTextField.setLayoutY(30);
                    addDomainTextField.setStyle(STYLE);

                    addFunctionPointsPane.getChildren().addAll(domainLabel, addDomainTextField);

                }
                addFunctionPointsPane.getChildren().addAll(meanLabel, StandardDeviationLabel, addAValueTextField, addBValueTextField);
            }
            default -> throw new UnsupportedOperationException("There is no such function");
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
                editAValueTextField.setStyle(POINTS_STYLE);
                editBValueTextField.setLayoutX(25);
                editBValueTextField.setLayoutY(50);
                editBValueTextField.setStyle(POINTS_STYLE);
                editCValueTextField.setLayoutX(125);
                editCValueTextField.setLayoutY(5);
                editCValueTextField.setStyle(POINTS_STYLE);
                editDValueTextField.setLayoutX(125);
                editDValueTextField.setLayoutY(50);
                editDValueTextField.setStyle(POINTS_STYLE);

                if (editDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(DISCRETE_DOMAIN)) {
                    domainLabel.setLayoutX(220);
                    domainLabel.setLayoutY(5);

                    editDomainTextField.setLayoutX(205);
                    editDomainTextField.setLayoutY(30);
                    editDomainTextField.setStyle(STYLE);

                    editFunctionPointsPane.getChildren().addAll(domainLabel, editDomainTextField);

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
                editAValueTextField.setStyle(POINTS_STYLE);
                editBValueTextField.setLayoutX(25);
                editBValueTextField.setLayoutY(50);
                editBValueTextField.setStyle(POINTS_STYLE);
                editCValueTextField.setLayoutX(125);
                editCValueTextField.setLayoutY(5);
                editCValueTextField.setStyle(POINTS_STYLE);

                if (editDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(DISCRETE_DOMAIN)) {
                    domainLabel.setLayoutX(220);
                    domainLabel.setLayoutY(5);

                    editDomainTextField.setLayoutX(205);
                    editDomainTextField.setLayoutY(30);
                    editDomainTextField.setStyle(STYLE);

                    editFunctionPointsPane.getChildren().addAll(domainLabel, editDomainTextField);

                }

                editFunctionPointsPane.getChildren().addAll(pointALabel, pointBLabel, pointCLabel, editAValueTextField, editBValueTextField, editCValueTextField);
            }
            case GAUSSIAN -> {

                meanLabel.setLayoutX(5);
                meanLabel.setLayoutY(5);
                StandardDeviationLabel.setLayoutX(5);
                StandardDeviationLabel.setLayoutY(50);

                editAValueTextField.setLayoutX(40);
                editAValueTextField.setLayoutY(5);
                editAValueTextField.setStyle(POINTS_STYLE);
                editBValueTextField.setLayoutX(40);
                editBValueTextField.setLayoutY(50);
                editBValueTextField.setStyle(POINTS_STYLE);

                if (editDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(DISCRETE_DOMAIN)) {
                    domainLabel.setLayoutX(220);
                    domainLabel.setLayoutY(5);

                    editDomainTextField.setLayoutX(205);
                    editDomainTextField.setLayoutY(30);
                    editDomainTextField.setStyle(STYLE);

                    editFunctionPointsPane.getChildren().addAll(domainLabel, editDomainTextField);

                }

                editFunctionPointsPane.getChildren().addAll(meanLabel, StandardDeviationLabel, editAValueTextField, editBValueTextField);
            }
            default -> throw new UnsupportedOperationException("There is no such function");
        }
    }

    private MembershipFunction getAddFunction(FuzzySet<?> variable, String name) {
        Domain domain;
        if (addDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(CONTINUOUS_DOMAIN)) {
            domain = variable.getDomain();
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

    private MembershipFunction getEditFunction(FuzzySet<?> variable, String name) {
        Domain domain;
        if (editDomainTypeChoiceBox.getSelectionModel().getSelectedItem().equals(CONTINUOUS_DOMAIN)) {
            domain = variable.getDomain();
        } else {
            domain = getEditDomain(DISCRETE_DOMAIN);
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
        if (DISCRETE_DOMAIN.equals(domainName)) {
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
        if (DISCRETE_DOMAIN.equals(domainName)) {
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