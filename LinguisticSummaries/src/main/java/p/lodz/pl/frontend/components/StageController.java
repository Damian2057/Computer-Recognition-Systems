package p.lodz.pl.frontend.components;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.*;

public class StageController implements Initializable {

    @FXML
    private AnchorPane scrollAttributes;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Map<String, Object> attributeMap = new HashMap<>();

        String[] ageScales = {"Young", "Adult", "Old"};
        String[] clothingScales = {"S", "M", "L"};

        attributeMap.put("Age", ageScales);
        attributeMap.put("Size", clothingScales);

        Set<String> attributeNames = attributeMap.keySet();

        int offsetY = 10;

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
            }

            offsetY += (retrievedScales.length + 1) * 22; // Adjust the Y position offset for the next attribute
        }
        scrollAttributes.setPrefHeight(offsetY + 10);
    }

}
