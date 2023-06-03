package p.lodz.pl.frontend.components;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class CustomCellFactory<T> implements Callback<TableColumn<T, Double>,
        TableCell<T, Double>> {

    @Override
    public TableCell<T, Double> call(TableColumn<T, Double> column) {
        return new TableCell<>() {
            @Override
            protected void updateItem(Double item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    String formattedValue;
                    if (item == 1.0) {
                        formattedValue = "1.00";
                    } else if (item > 0.99) {
                        formattedValue = ">" + "0.99";
                    } else if (item == 0.0) {
                        formattedValue = "0.00";
                    } else if (item < 0.01) {
                        formattedValue = "~" + "0.00";
                    } else formattedValue = String.format("%.2f", item);
                    setText(formattedValue);
                }
            }
        };
    }
}
