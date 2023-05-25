package p.lodz.pl.frontend.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticVariable;
import p.lodz.pl.backend.fuzzy.quantifier.Quantifier;
import p.lodz.pl.backend.fuzzy.summary.Summary;
import p.lodz.pl.backend.model.PolicyEntity;
import p.lodz.pl.backend.repository.MockRepository;
import p.lodz.pl.backend.fuzzy.linguistic.LinguisticLabel;

import java.net.URL;
import java.util.*;

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
    private TableColumn<Summary, String> formColumn;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        MockRepository mockRepository = new MockRepository();
        List<LinguisticVariable<PolicyEntity>> linguisticVariablesList = mockRepository.findAllLinguisticVariables();
        List<Quantifier> quantifiersList = mockRepository.findAllQuantifiers();


        summaryTableView.getColumns().setAll(formColumn, summaryColumn, averageQMColumn,degreeofTruthColumn, degreeOfImprecisionColumn, degreeOfCoveringColumn, degreeOfAppropriatenessColumn, lengthOfSummaryColumn, degreeOfQuantifierImprecisionColumn, degreeOfQuantifierRelativeCardinalityColumn, degreeOfSummarizerRelativeCardinalityColumn, degreeOfQualifierImprecisionColumn, degreeOfQualifierRelativeCardinalityColumn, lengthOfQualifierColumn);

        int linguisticOffsetY = 10;
        List selectedSummarizers = new ArrayList();

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
                        String selectedEtiquette = etiquetteLabelName.getLabelName();
                        selectedSummarizers.add(selectedEtiquette);
                        System.out.println("Selected etiquettes: " + selectedSummarizers);
                    } else {
                        String unselectedScale = etiquetteLabelName.getLabelName();
                        selectedSummarizers.remove(unselectedScale);
                        System.out.println("Updated etiquettes: " + selectedSummarizers);
                    }
                });

                i++;
            }
            linguisticOffsetY += (linguisticVariablesList.size() + 1) * 12;
        }
        scrollAttributes.setPrefHeight(linguisticOffsetY + 10);

        int quantifierOffsetY = 10;
        List selectedQuantifiers = new ArrayList();
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
                    String selectedQuantifier = quantifier.getLabelName();
                    selectedQuantifiers.add(selectedQuantifier);
                    System.out.println("Selected quantifiers: " + selectedQuantifiers);
                } else {
                    String unselectedScale = quantifier.getLabelName();
                    selectedQuantifiers.remove(unselectedScale);
                    System.out.println("Updated quantifiers: " + selectedQuantifiers);
                }
            });

            i++;
            quantifierOffsetY += 15;
        }
        scrollQuantifiers.setPrefHeight((quantifierOffsetY) * 3);
    }

    public void generateSummaries(ActionEvent event) {
    }

    public void goToAdvancedSettings(ActionEvent event) {
    }

    public void goToMultiSubject(ActionEvent event) {
    }

    public void openWeightsWindow(ActionEvent event) {
    }


}
