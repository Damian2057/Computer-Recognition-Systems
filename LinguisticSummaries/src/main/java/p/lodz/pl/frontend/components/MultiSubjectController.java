package p.lodz.pl.frontend.components;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticVariable;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;
import p.lodz.pl.backend.fuzzy.summary.MultiSubjectLinguisticSummary;
import p.lodz.pl.backend.fuzzy.summary.Summary;
import p.lodz.pl.backend.fuzzy.util.Pair;
import p.lodz.pl.backend.fuzzy.util.SubjectExtractor;
import p.lodz.pl.backend.model.PolicyEntity;
import p.lodz.pl.backend.repository.DBConnection;
import p.lodz.pl.backend.repository.Dao;
import p.lodz.pl.backend.repository.FileOperator;
import p.lodz.pl.backend.repository.MockRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class MultiSubjectController {
    @FXML
    private AnchorPane scrollAttributes;
    @FXML
    private Button generateButton;
    @FXML
    private AnchorPane scrollQuantifiers;
    @FXML
    private TableView<Summary> summaryTableView;
    @FXML
    private TableColumn<Summary, Integer> formColumn;
    @FXML
    private TableColumn<Summary, String> summaryColumn;
    @FXML
    private ChoiceBox<String> firstSubjectChoiceBox;
    @FXML
    private ChoiceBox<String> secondSubjectChoiceBox;
    @FXML
    private Button advancedSettingsButton;
    @FXML
    private Button singleSubjectButton;
    @FXML
    private TableColumn<Summary, Double> degreeOfTruthColumn;
    @FXML
    private Button saveToFileButton;

    private Scene previousScene;

    private StageController stageController;

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
        this.mockRepository = stageController.getMockRepository();
        this.linguisticVariablesList = mockRepository.findAllLinguisticVariables();
        this.allQuantifiers = mockRepository.findAllQuantifiers();
    }

    private final Dao dao = new DBConnection();
    private final List<LinguisticLabel<PolicyEntity>> selectedQualifiers = new ArrayList<>();
    private MockRepository mockRepository = null;
    private List<LinguisticVariable<PolicyEntity>> linguisticVariablesList = null;
    private List<Quantifier> allQuantifiers = null;
    private List<Summary> savedSummaries = new ArrayList<>();

    public void initializeMultiSubjectView() {
        selectedQualifiers.clear();
        linguisticVariablesList = mockRepository.findAllLinguisticVariables();

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
    }

    public void generateMultiSubjectSummaries() {
        formColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().form()));
        summaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().summary()));
        degreeOfTruthColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(1)).asObject());

        summaryTableView.getItems().clear();

        String selectedSubject = "policyholders who are young";

        Pair<List<PolicyEntity>, List<PolicyEntity>> pair =
                SubjectExtractor.extract(dao.getPolicies(), getPredicateBySubject(selectedSubject));

        for (int i=0; i< allQuantifiers.size(); i++) {
            MultiSubjectLinguisticSummary<PolicyEntity> linguisticSummary
                    = new MultiSubjectLinguisticSummary<>(allQuantifiers.get(i),
                    selectedQualifiers,
                    "cars",
                    "policyholders",
                    pair.getFirst(),
                    pair.getSecond());

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

    private Predicate<PolicyEntity> getPredicateBySubject(String subject) {
        String name = "";
        if ("policyholders who are young".equals(subject)) {
            name = "young adult";
        }
        LinguisticLabel<PolicyEntity> filter = mockRepository.findLinguisticLabelByName(name);

        return p -> filter.getMemberShip(p) > 0.0;
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

    public void saveSummariesToFile() {
        FileOperator fileOperator = new FileOperator();
        fileOperator.writeToFile(savedSummaries);
    }

    public void setPreviousScene(Scene scene) {
        previousScene = scene;
    }
}
