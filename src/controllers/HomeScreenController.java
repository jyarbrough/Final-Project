package controllers;

import contexts.EmployeeContext;
import contexts.PickupOrDeliveryContext;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import stages.PickupDeliveryStage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {

    public Button newOrderButton;
    public Button pickupButton;
    public Button deliveryButton;
    public Button openOrdersButton;
    public Button reviseOrderButton;
    public Button openRegisterButton;
    public TextField loggedInTextField;
    public TextField idTextField;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        EmployeeContext employeeContext = EmployeeContext.getInstance();
        String employeeName = employeeContext.getEmployeeLoggedInName();
        String employeeId = employeeContext.getEmployeeId();

        loggedInTextField.setText(employeeName);
        idTextField.setText(employeeId);

        newOrderButton.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                PickupDeliveryStage pickupDeliveryStage = new PickupDeliveryStage();
                pickupDeliveryStage.stage(newOrderButton);
            }
        });
    }
}
