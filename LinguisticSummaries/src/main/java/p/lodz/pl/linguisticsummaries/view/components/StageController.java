package p.lodz.pl.linguisticsummaries.view.components;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import p.lodz.pl.linguisticsummaries.backend.service.PolicyService;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class StageController implements Initializable {

    private final PolicyService service;

    @Autowired
    public StageController(PolicyService service) {
        this.service = service;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void onClick(ActionEvent actionEvent) {
        service.Test();
    }
}
