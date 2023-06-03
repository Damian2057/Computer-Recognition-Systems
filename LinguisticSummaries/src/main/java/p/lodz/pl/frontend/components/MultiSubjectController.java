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
import lombok.extern.java.Log;
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
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Log
public class MultiSubjectController {
    @FXML
    private AnchorPane scrollAttributes;
    @FXML
    private TableView<Summary> summaryTableView;
    @FXML
    private TableColumn<Summary, Integer> formColumn;
    @FXML
    private TableColumn<Summary, String> summaryColumn;
    @FXML
    private ChoiceBox<String> firstSubjectChoiceBox;
    @FXML
    private TableColumn<Summary, Double> degreeOfTruthColumn;

    private Scene previousScene;

    private StageController stageController;

    private static final String SUBJECT1 = "cars with sporty height";
    private static final String SUBJECT2 = "cars with around half of the year policy tenure";
    private static final String SUBJECT3 = "cars with medium aged policyholders";
    private static final String SUBJECT4 = "policyholders with combi length cars";
    private static final String SUBJECT5 = "policyholders that have to 3 months policy tenure";
    private static final String SUBJECT6 = "family car width cars that have adult policyholders";
    private static final String SUBJECT7 = "policyholders who are young";

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
    private final List<Summary> savedSummaries = new ArrayList<>();

    public void initializeMultiSubjectView() {
        selectedQualifiers.clear();
        linguisticVariablesList = mockRepository.findAllLinguisticVariables();

        firstSubjectChoiceBox.getItems().addAll(mockRepository.findAllSubjects());

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
        savedSummaries.clear();
        summaryTableView.getItems().clear();
        formColumn.setCellValueFactory(cellData -> Bindings.createObjectBinding(() -> cellData.getValue().form()));
        summaryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().summary()));
        degreeOfTruthColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().quality().get(0)).asObject());
        degreeOfTruthColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String formattedValue;
                    if (item == 0.9) {
                        formattedValue = "~" + "1.00";
                    } else if (item > 0.99) {
                        formattedValue = ">" + String.format("%.2f", item);
                    } else {
                        formattedValue = "~" + String.format("%.2f", item);
                    }
                    setText(formattedValue);
                }
            }
        });
        summaryTableView.getItems().clear();

        List<Quantifier> filteredQuantifiers = allQuantifiers.stream().filter(quantifier -> !quantifier.isAbsolute()).collect(Collectors.toList());

        String selectedSubject = firstSubjectChoiceBox.getSelectionModel().getSelectedItem();

        Pair<List<PolicyEntity>, List<PolicyEntity>> pair =
                SubjectExtractor.extract(dao.getPolicies(), getPredicateBySubject(selectedSubject));

        for (Quantifier quantifier : filteredQuantifiers) {
            MultiSubjectLinguisticSummary<PolicyEntity> linguisticSummary
                    = new MultiSubjectLinguisticSummary<>(quantifier,
                    selectedQualifiers,
                    selectedSubject,
                    "rest",
                    pair.getFirst(),
                    pair.getSecond());
            List<Summary> summaries = linguisticSummary.generateSummary();
            log.info("Generated summaries for: " + quantifier.getLabelName());

            summaryTableView.getItems().addAll(summaries);
            savedSummaries.addAll(summaries);
        }

        MultiSubjectLinguisticSummary<PolicyEntity> linguisticSummary
                = new MultiSubjectLinguisticSummary<>(null,
                selectedQualifiers,
                selectedSubject,
                "rest",
                pair.getFirst(),
                pair.getSecond());
        List<Summary> summaries = linguisticSummary.generateFourthForm();
        log.info("Generated summaries for Fourth Form");

        summaryTableView.getItems().addAll(summaries);
        savedSummaries.addAll(summaries);
    }

    private Predicate<PolicyEntity> getPredicateBySubject(String subject) {
        String name;
        if (SUBJECT1.equals(subject)) {
            name = "sporty";
        } else if (SUBJECT2.equals(subject)) {
            name = "around half of the year";
        } else if (SUBJECT3.equals(subject)) {
            name = "medium age";
        } else if (SUBJECT4.equals(subject)) {
            name = "combi";
        } else if (SUBJECT5.equals(subject)) {
            name = "to 3 months";
        } else if (SUBJECT6.equals(subject)) {
            name = "family car";
        } else if (SUBJECT7.equals(subject)) {
            name = "young adult";
        } else throw new UnsupportedOperationException("No such subject available");

        LinguisticLabel<PolicyEntity> filter = mockRepository.findLinguisticLabelByName(name);

        return p -> filter.getMemberShip(p) > 0.0;
    }

    public void goToAdvancedSettings(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/components/advanced.fxml"));
            Parent advancedRoot = loader.load();
            AdvancedController advancedController = loader.getController();
            advancedController.setStageController(stageController);
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

    public void goToSingleSubject(ActionEvent event) {
        try {
            Parent advancedRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/components/view.fxml")));
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
