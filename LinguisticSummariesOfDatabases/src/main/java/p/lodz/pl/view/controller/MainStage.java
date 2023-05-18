package p.lodz.pl.view.controller;

import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import p.lodz.pl.logic.service.PolicyService;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainStage implements Initializable {

    private final PolicyService policyService;

    @Autowired
    public MainStage(PolicyService policyService) {
        this.policyService = policyService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
