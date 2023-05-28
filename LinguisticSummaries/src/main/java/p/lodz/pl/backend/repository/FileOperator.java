package p.lodz.pl.backend.repository;

import p.lodz.pl.backend.fuzzy.summary.Summary;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileOperator {

    public void writeToFile(List<Summary> savedSummaries) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a directory");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath() + "/output.txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write(toString(savedSummaries));
                System.out.println("String saved to file successfully.");
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }
    }

    private String toString(List<Summary> savedSummaries) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Summary summary : savedSummaries) {
            stringBuilder.append(summary.form() + "," ).append(summary.summary()).append("\n");
            stringBuilder.append("S: " + summary.quality().get(0) + "\n");
            stringBuilder.append("T1: " + summary.quality().get(1) + "\n");
            stringBuilder.append("T2: " + summary.quality().get(2) + "\n");
            stringBuilder.append("T3: " + summary.quality().get(3) + "\n");
            stringBuilder.append("T4: " + summary.quality().get(4) + "\n");
            stringBuilder.append("T5: " + summary.quality().get(5) + "\n");
            stringBuilder.append("T6: " + summary.quality().get(6) + "\n");
            stringBuilder.append("T7: " + summary.quality().get(7) + "\n");
            stringBuilder.append("T8: " + summary.quality().get(8) + "\n");
            stringBuilder.append("T9: " + summary.quality().get(9) + "\n");
            stringBuilder.append("T10: " + summary.quality().get(10) + "\n");
            stringBuilder.append("T11: " + summary.quality().get(11) + "\n");
        }
        return stringBuilder.toString();
    }
}
