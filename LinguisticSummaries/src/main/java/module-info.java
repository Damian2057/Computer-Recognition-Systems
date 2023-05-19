module LinguisticSummaries {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.datatransfer;
    requires java.desktop;
    requires java.logging;

    opens p.lodz.pl.frontend to javafx.fxml, javafx.controls, javafx.graphics;
    opens p.lodz.pl.frontend.comonents to javafx.fxml, javafx.controls, javafx.graphics;
    exports p.lodz.pl.frontend;
    exports p.lodz.pl.frontend.comonents;
}