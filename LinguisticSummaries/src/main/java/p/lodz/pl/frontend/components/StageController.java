package p.lodz.pl.frontend.components;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

public class StageController implements Initializable {

    @FXML
    private TableColumn<SummaryData, String> formColumn;
    @FXML
    private TableView<SummaryData> summaryTableView;
    @FXML
    private TableColumn<SummaryData, String> summaryColumn;
    @FXML
    private TableColumn<SummaryData, String> degreeColumn;
    @FXML
    private ChoiceBox<String> relativeChoiceBox;
    @FXML
    private ChoiceBox<String> absoluteChoiceBox;
    @FXML
    private AnchorPane scrollAttributes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ArrayList<String> absoluteQuantifier = new ArrayList<>();
        absoluteQuantifier.add("Less than 9000");
        absoluteQuantifier.add("About 1/4");
        absoluteQuantifier.add("About half");
        absoluteQuantifier.add("Over 20000");
        absoluteQuantifier.add("About 3/4");
        absoluteQuantifier.add("More than 3/4");

        ArrayList<String> relativeQuantifier = new ArrayList<>();
        relativeQuantifier.add("Almost none");
        relativeQuantifier.add("Some");
        relativeQuantifier.add("About half");
        relativeQuantifier.add("Many");
        relativeQuantifier.add("Almost all");

        absoluteChoiceBox.getItems().clear();
        relativeChoiceBox.getItems().clear();

        formColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("I"));
        summaryColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("Some cars have sporty height"));
        degreeColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper("[0.62]"));

        formColumn.setCellFactory(column -> new TableCell<SummaryData, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });

        summaryColumn.setCellFactory(column -> new TableCell<SummaryData, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });

        degreeColumn.setCellFactory(column -> new TableCell<SummaryData, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item);
                }
            }
        });

        ObservableList<SummaryData> data = FXCollections.observableArrayList();
        data.add(new SummaryData("I","Some cars have sporty height", "0.62"));

        summaryTableView.setItems(data);

        summaryTableView.getColumns().setAll(formColumn, summaryColumn, degreeColumn);


        for (String absoluteLabels: absoluteQuantifier) {
            absoluteChoiceBox.getItems().add(absoluteLabels);
        }
        for (String relativeLabels: relativeQuantifier) {
            relativeChoiceBox.getItems().add(relativeLabels);
        }

        relativeChoiceBox.setValue(relativeChoiceBox.getItems().get(1));

        relativeChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                absoluteChoiceBox.getSelectionModel().clearSelection();
            }
        });

        absoluteChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                relativeChoiceBox.getSelectionModel().clearSelection();
            }
        });

        Map<String, Object> attributeMap = new HashMap<>();

        String[] ageScales = {"Young adult", "Adult", "Medium age", "Old"};
        String[] clothingScales = {"Small car", "Hatchback", "Small suv", "Combi"};

        attributeMap.put("Age of policyholder", ageScales);
        attributeMap.put("Length", clothingScales);

        Set<String> attributeNames = attributeMap.keySet();

        int offsetY = 10;

        ArrayList selectedScales = new ArrayList();

        for (String attributeName : attributeNames) {

            String[] retrievedScales = (String[]) attributeMap.get(attributeName);

            Label attributeNameLabel = new Label(attributeName);
            attributeNameLabel.setLayoutX(15);
            attributeNameLabel.setLayoutY(offsetY);

            scrollAttributes.getChildren().add(attributeNameLabel);

            offsetY += 30;

            for (int i = 0; i < retrievedScales.length; i++) {
                Label label = new Label(retrievedScales[i]);
                CheckBox checkBox = new CheckBox();

                label.setLayoutX(60);
                label.setLayoutY(offsetY + i * 30);

                checkBox.setLayoutX(30);
                checkBox.setLayoutY(offsetY + i * 30);

                scrollAttributes.getChildren().addAll(label, checkBox);

                checkBox.setOnAction(event -> {
                    if (checkBox.isSelected()) {
                        String selectedScale = label.getText();
                        selectedScales.add(selectedScale);
                        System.out.println("Selected scale: " + selectedScales);
                    } else {
                        String unselectedScale = label.getText();
                        selectedScales.remove(unselectedScale);
                        System.out.println("Updated scales: " + selectedScales);
                    }
                });
            }

            offsetY += (retrievedScales.length + 1) * 24;
        }
        scrollAttributes.setPrefHeight(offsetY + 10);
    }

}
