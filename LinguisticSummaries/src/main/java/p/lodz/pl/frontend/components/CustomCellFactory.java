package p.lodz.pl.frontend.components;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class CustomCellFactory<Summary> implements Callback<TableColumn<Summary, Double>, TableCell<Summary, Double>> {

    @Override
    public TableCell<Summary, Double> call(TableColumn<Summary, Double> column) {
        return new TableCell<Summary, Double>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String formattedValue = formatValue(item);
                    setText(formattedValue);
                }
            }
        };
    }

    private String formatValue(Double value) {
        if (value == 0.9) {
            return "~1.00";
        } else if (value > 0.99) {
            return ">" + String.format("%.2f", value);
        } else {
            return "~" + String.format("%.2f", value);
        }
    }
}
