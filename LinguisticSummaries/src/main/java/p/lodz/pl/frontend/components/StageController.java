package p.lodz.pl.frontend.components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticVariable;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;
import p.lodz.pl.backend.fuzzy.summary.SingleSubjectLinguisticSummary;
import p.lodz.pl.backend.fuzzy.summary.Summary;
import p.lodz.pl.backend.model.PolicyEntity;
import p.lodz.pl.backend.repository.DBConnection;
import p.lodz.pl.backend.repository.Dao;
import p.lodz.pl.backend.repository.FileOperator;
import p.lodz.pl.backend.repository.MockRepository;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class StageController implements Initializable {

//    @FXML
//    private TableColumn<Summary, Boolean> checkBoxColumn;
    @FXML
    private Button generateButton;
    @FXML
    private Button goToAdvancedButton;
    @FXML
    private Button goToMultiSubjectButton;
    @FXML
    private Button modifyWeightsButton;
    @FXML
    private TableView<Summary> summaryTableView;
    @FXML
    private TableColumn<Summary, Integer> formColumn;
    @FXML
    private TableColumn<Summary, String> summaryColumn;
    @FXML
    private TableColumn<Summary, Double> averageQMColumn;
    @FXML
    private TableColumn<Summary, Double> degreeofTruthColumn;
    @FXML
    private TableColumn<Summary, Double> degreeOfImprecisionColumn;
    @FXML
    private TableColumn<Summary, Double> degreeOfCoveringColumn;
    @FXML
    private TableColumn<Summary, Double> degreeOfAppropriatenessColumn;
    @FXML
    private TableColumn<Summary, Double> lengthOfSummaryColumn;
    @FXML
    private TableColumn<Summary, Double> degreeOfQuantifierImprecisionColumn;
    @FXML
    private TableColumn<Summary, Double> degreeOfQuantifierRelativeCardinalityColumn;
    @FXML
    private TableColumn<Summary, Double> degreeOfSummarizerRelativeCardinalityColumn;
    @FXML
    private TableColumn<Summary, Double> degreeOfQualifierImprecisionColumn;
    @FXML
    private TableColumn<Summary, Double> degreeOfQualifierRelativeCardinalityColumn;
    @FXML
    private TableColumn<Summary, Double> lengthOfQualifierColumn;
    @FXML
    private AnchorPane scrollAttributes;
    @FXML
    private AnchorPane scrollQuantifiers;

    private Scene previousScene;

    private List<Summary> savedSummaries;

    private  List<LinguisticLabel<PolicyEntity>> selectedSummarizers = new ArrayList();
    private List<Quantifier> selectedQuantifiers = new ArrayList();

    private Dao dao = new DBConnection();
    private List<Double> weights = new ArrayList<>();
    private MockRepository mockRepository = new MockRepository();
    private SingleSubjectLinguisticSummary<PolicyEntity> linguisticSummary;

    @SneakyThrows
    public void initializeView(MockRepository mockRepository) {
        List<LinguisticVariable<PolicyEntity>> linguisticVariablesList = mockRepository.findAllLinguisticVariables();
        List<Quantifier> quantifiersList = mockRepository.findAllQuantifiers();

        int linguisticOffsetY = 10;

        for (LinguisticVariable<PolicyEntity> linguisticVariable : linguisticVariablesList) {

            Label attributeLabel = new Label(linguisticVariable.getLinguisticVariableName());
            attributeLabel.setLayoutX(15);
            attributeLabel.setLayoutY(linguisticOffsetY);

            scrollAttributes.getChildren().add(attributeLabel);
            linguisticOffsetY += 30;

            int i=0;

            for (LinguisticLabel<PolicyEntity> etiquetteLabelName : linguisticVariable.getLabels()) {

                Label etiquetteLabel = new Label(etiquetteLabelName.getLabelName());
                CheckBox checkBox = new CheckBox();

                etiquetteLabel.setLayoutX(60);
                etiquetteLabel.setLayoutY(linguisticOffsetY + i * 30);

                checkBox.setLayoutX(30);
                checkBox.setLayoutY(linguisticOffsetY + i * 30);

                scrollAttributes.getChildren().addAll(etiquetteLabel, checkBox);

                checkBox.setOnAction(event -> {
                    if (checkBox.isSelected()) {
                        LinguisticLabel<PolicyEntity> selectedEtiquette = etiquetteLabelName;
                        selectedSummarizers.add(selectedEtiquette);
                    } else {
                        LinguisticLabel<PolicyEntity> unselectedEtiquette = etiquetteLabelName;
                        selectedSummarizers.remove(unselectedEtiquette);
                    }
                });

                i++;
            }
            linguisticOffsetY += (linguisticVariablesList.size() + 1) * 12;
        }
        scrollAttributes.setPrefHeight(linguisticOffsetY + 10);

        int quantifierOffsetY = 10;

        int i = 0;
        for (Quantifier quantifier : quantifiersList) {

            Label quantifierLabel = new Label(quantifier.getLabelName());
            quantifierLabel.setLayoutX(40);
            quantifierLabel.setLayoutY(quantifierOffsetY + i * 30);

            CheckBox checkBox = new CheckBox();

            checkBox.setLayoutX(15);
            checkBox.setLayoutY(quantifierOffsetY + i * 30);

            scrollQuantifiers.getChildren().addAll(quantifierLabel, checkBox);

            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    Quantifier selectedQuantifier = quantifier;
                    selectedQuantifiers.add(selectedQuantifier);
                } else {
                    Quantifier unselectedQuantifier = quantifier;
                    selectedQuantifiers.remove(unselectedQuantifier);
                }
            });

            i++;
            quantifierOffsetY += 15;
        }
        scrollQuantifiers.setPrefHeight((quantifierOffsetY) * 3);

        List<Double> defaultWeights = new ArrayList<>();

        defaultWeights.add(0.3);
        defaultWeights.add(0.07);
        defaultWeights.add(0.07);
        defaultWeights.add(0.07);
        defaultWeights.add(0.07);
        defaultWeights.add(0.07);
        defaultWeights.add(0.07);
        defaultWeights.add(0.07);
        defaultWeights.add(0.07);
        defaultWeights.add(0.07);
        defaultWeights.add(0.07);

        setWeights(defaultWeights);
    }

    public void generateSummaries(ActionEvent event) {

//        checkBoxColumn.setCellValueFactory(cellData -> new SimpleBooleanProperty(cellData.getValue().selected()));
//        checkBoxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkBoxColumn));
        formColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().form()));
        summaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().summary()));
        averageQMColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(0)).asObject());
        degreeofTruthColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(1)).asObject());
        degreeOfImprecisionColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(2)).asObject());
        degreeOfCoveringColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(3)).asObject());
        degreeOfAppropriatenessColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(4)).asObject());
        lengthOfSummaryColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(5)).asObject());
        degreeOfQuantifierImprecisionColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(6)).asObject());
        degreeOfQuantifierRelativeCardinalityColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(7)).asObject());
        degreeOfSummarizerRelativeCardinalityColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(8)).asObject());
        degreeOfQualifierImprecisionColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(9)).asObject());
        degreeOfQualifierRelativeCardinalityColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(10)).asObject());
        lengthOfQualifierColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(11)).asObject());

        summaryTableView.getItems().clear();

        for (int i=0; i< selectedQuantifiers.size(); i++) {

            linguisticSummary = new SingleSubjectLinguisticSummary<>(selectedQuantifiers.get(i),
                    selectedSummarizers,
                    "cars",
                    dao.getPolicies(),
                    weights);
//            System.out.println("Wagi w generate summaries" + weights);
            List<Summary> summaries = linguisticSummary.generateSummary();
            for (Summary s : summaries) {
                String result = s.summary() + " " + s.quality().get(0);
                System.out.println(result);
                Summary summary = new Summary(s.form(), s.summary(), s.quality());
                summaryTableView.getItems().add(summary);
            }
            savedSummaries = summaries;
        }
    }

    public void goToAdvancedSettings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/advanced.fxml"));
            Parent advancedRoot = loader.load();
            AdvancedController advancedController = loader.getController();
            advancedController.setStageController(this);
            advancedController.inititalizeSummarizersAndQuantifiers();

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

    public void goToMultiSubject(ActionEvent event) {
        try {
            Parent advancedRoot = FXMLLoader.load(getClass().getResource("/components/multiSubject.fxml"));
            Scene advancedScene = new Scene(advancedRoot);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(advancedScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openWeightsWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/weightedQM.fxml"));
        Parent fileRoot = loader.load();

        Stage fileStage = new Stage();
        fileStage.setTitle("Weights");

        Scene fileScene = new Scene(fileRoot);
        fileStage.setScene(fileScene);

        WeightedQMController weightedQMController = loader.getController();
        weightedQMController.setStageController(this);
        weightedQMController.initializeWeights();

        fileStage.show();
    }

    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }

    public void setMockRepository(MockRepository mockRepository) {
        this.mockRepository = mockRepository;
        initializeView(mockRepository);
    }

    public MockRepository getMockRepository() {
        return mockRepository;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeView(new MockRepository());
    }

    public void saveSummariesToFile(ActionEvent event) {
        FileOperator fileOperator = new FileOperator();
        fileOperator.writeToFile(savedSummaries);
    }
}
