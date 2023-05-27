package p.lodz.pl.frontend.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.util.ArrayList;
import java.util.List;

public class WeightedQMController {
    @FXML
    private TextField degreeOfTruthTF;
    @FXML
    private TextField degreeOfImprecisionTF;
    @FXML
    private TextField degreeOfCoveringTF;
    @FXML
    private TextField degreeOfAppropriatenessTF;
    @FXML
    private TextField lengthOfSummaryTF;
    @FXML
    private TextField degreeOfQuantifierImprecisionTF;
    @FXML
    private TextField degreeOfQuantifierRelativeCardinalityTF;
    @FXML
    private TextField degreeOfSummarizerRelativeCardinalityTF;
    @FXML
    private TextField degreeOfQualifierImprecisionTF;
    @FXML
    private TextField degreeOfQualifierRelativeCardinalityTF;
    @FXML
    private TextField lengthOfQualifierTF;
    @FXML
    private Button saveWeightsButton;

    private StageController stageController;

    public void setStageController(StageController stageController) {
        this.stageController = stageController;
    }

    public void initializeWeights() {
        degreeOfTruthTF.setText(Double.toString(0.3));
        degreeOfImprecisionTF.setText(Double.toString(0.7));
        degreeOfCoveringTF.setText(Double.toString(0.7));
        degreeOfAppropriatenessTF.setText(Double.toString(0.7));
        lengthOfSummaryTF.setText(Double.toString(0.7));
        degreeOfQuantifierImprecisionTF.setText(Double.toString(0.7));
        degreeOfQuantifierRelativeCardinalityTF.setText(Double.toString(0.7));
        degreeOfSummarizerRelativeCardinalityTF.setText(Double.toString(0.7));
        degreeOfQualifierImprecisionTF.setText(Double.toString(0.7));
        degreeOfQualifierRelativeCardinalityTF.setText(Double.toString(0.7));
        lengthOfQualifierTF.setText(Double.toString(0.7));
    }

    public void saveWeights(ActionEvent event) {
        List<Double> updatedWeights = new ArrayList<>();

        updatedWeights.add(Double.parseDouble(degreeOfTruthTF.getText()));
        updatedWeights.add(Double.parseDouble(degreeOfImprecisionTF.getText()));
        updatedWeights.add(Double.parseDouble(degreeOfCoveringTF.getText()));
        updatedWeights.add(Double.parseDouble(degreeOfAppropriatenessTF.getText()));
        updatedWeights.add(Double.parseDouble(lengthOfSummaryTF.getText()));
        updatedWeights.add(Double.parseDouble(degreeOfQuantifierImprecisionTF.getText()));
        updatedWeights.add(Double.parseDouble(degreeOfQuantifierRelativeCardinalityTF.getText()));
        updatedWeights.add(Double.parseDouble(degreeOfSummarizerRelativeCardinalityTF.getText()));
        updatedWeights.add(Double.parseDouble(degreeOfQualifierImprecisionTF.getText()));
        updatedWeights.add(Double.parseDouble(degreeOfQualifierRelativeCardinalityTF.getText()));
        updatedWeights.add(Double.parseDouble(lengthOfQualifierTF.getText()));

        stageController.setWeights(updatedWeights);
    }
}
