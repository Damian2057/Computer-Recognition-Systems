package p.lodz.pl.frontend.components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.util.Callback;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticVariable;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;
import p.lodz.pl.backend.fuzzy.summary.SingleSubjectLinguisticSummary;
import p.lodz.pl.backend.fuzzy.summary.Summary;
import p.lodz.pl.backend.model.PolicyEntity;
import p.lodz.pl.backend.repository.DBConnection;
import p.lodz.pl.backend.repository.Dao;
import p.lodz.pl.backend.repository.FileOperator;
import p.lodz.pl.backend.repository.MockRepository;
import p.lodz.pl.frontend.components.CustomCellFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Log
public class StageController implements Initializable {

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

    private final List<Summary> savedSummaries = new ArrayList<>();
    private final List<LinguisticLabel<PolicyEntity>> selectedQualifiers = new ArrayList<>();
    private final Dao dao = new DBConnection();
    private List<Double> weights = new ArrayList<>();
    private MockRepository mockRepository = new MockRepository();
    private SingleSubjectLinguisticSummary<PolicyEntity> linguisticSummary;
    private final List<Quantifier> allQuantifiers = mockRepository.findAllQuantifiers();

    @SneakyThrows
    public void initializeView(MockRepository mockRepository) {
        selectedQualifiers.clear();
        List<LinguisticVariable<PolicyEntity>> linguisticVariablesList = mockRepository.findAllLinguisticVariables();

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
                        selectedQualifiers.add(etiquetteLabelName);
                    } else {
                        selectedQualifiers.remove(etiquetteLabelName);
                    }
                });

                i++;
            }
            linguisticOffsetY += (linguisticVariablesList.size() + 1) * 12;
        }
        scrollAttributes.setPrefHeight(linguisticOffsetY + 10);

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

    public void generateSummaries() {
        summaryTableView.getItems().clear();
        savedSummaries.clear();
        formColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().form()));
        summaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().summary()));
        averageQMColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(0)).asObject());
        averageQMColumn.setCellFactory(new CustomCellFactory<>());
        degreeofTruthColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(1)).asObject());
        degreeofTruthColumn.setCellFactory(new CustomCellFactory<>());
        degreeOfImprecisionColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(2)).asObject());
        degreeOfImprecisionColumn.setCellFactory(new CustomCellFactory<>());
        degreeOfCoveringColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(3)).asObject());
        degreeOfCoveringColumn.setCellFactory(new CustomCellFactory<>());
        degreeOfAppropriatenessColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(4)).asObject());
        degreeOfAppropriatenessColumn.setCellFactory(new CustomCellFactory<>());
        lengthOfSummaryColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(5)).asObject());
        lengthOfSummaryColumn.setCellFactory(new CustomCellFactory<>());
        degreeOfQuantifierImprecisionColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(6)).asObject());
        degreeOfQuantifierImprecisionColumn.setCellFactory(new CustomCellFactory<>());
        degreeOfQuantifierRelativeCardinalityColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(7)).asObject());
        degreeOfQuantifierRelativeCardinalityColumn.setCellFactory(new CustomCellFactory<>());
        degreeOfSummarizerRelativeCardinalityColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(8)).asObject());
        degreeOfSummarizerRelativeCardinalityColumn.setCellFactory(new CustomCellFactory<>());
        degreeOfQualifierImprecisionColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(9)).asObject());
        degreeOfQualifierImprecisionColumn.setCellFactory(new CustomCellFactory<>());
        degreeOfQualifierRelativeCardinalityColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(10)).asObject());
        degreeOfQualifierRelativeCardinalityColumn.setCellFactory(new CustomCellFactory<>());
        lengthOfQualifierColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(11)).asObject());
        lengthOfQualifierColumn.setCellFactory(new CustomCellFactory<>());

        summaryTableView.getItems().clear();
        List<PolicyEntity> entities = dao.getPolicies();

        for (Quantifier quantifier : allQuantifiers) {
            linguisticSummary = new SingleSubjectLinguisticSummary<>(quantifier,
                    selectedQualifiers,
                    "cars",
                    entities,
                    weights);
            List<Summary> summaries = linguisticSummary.generateSummary();
            log.info("Generated summaries for: " + quantifier.getLabelName());
            summaryTableView.getItems().addAll(summaries);
            savedSummaries.addAll(summaries);
        }
    }

    public void goToAdvancedSettings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/advanced.fxml"));
            Parent advancedRoot = loader.load();
            AdvancedController advancedController = loader.getController();
            advancedController.setStageController(this);
            advancedController.inititalizeQualifiersAndQuantifiers();

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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/multiSubject.fxml"));
            Parent multiSubjectRoot = loader.load();
            MultiSubjectController multiSubjectController = loader.getController();
            multiSubjectController.setStageController(this);
            multiSubjectController.initializeMultiSubjectView();

            Scene currentScene = ((Node) event.getSource()).getScene();
            multiSubjectController.setPreviousScene(currentScene);
            Scene advancedScene = new Scene(multiSubjectRoot);
            Stage currentStage = (Stage) currentScene.getWindow();
            currentStage.setScene(advancedScene);

            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openWeightsWindow() throws IOException {
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

    public void saveSummariesToFile() {
        FileOperator fileOperator = new FileOperator();
        fileOperator.writeToFile(savedSummaries);
    }
}
