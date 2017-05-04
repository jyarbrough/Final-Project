package controllers;

import contexts.ApplicationContext;
import enums.OperationMode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import models.EmployeeModel;
import stages.CustomerInfoStage;
import stages.HomeScreenStage;

import java.net.URL;
import java.util.ResourceBundle;

public class PickupOrDeliveryController implements Initializable {

    public Button pickupButton;
    public Button backButton;
    public TextField loggedInTextField;
    public TextField idTextField;
    public Button deliveryButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CustomerInfoStage customerInfoStage = new CustomerInfoStage();
        HomeScreenStage homeScreenStage = new HomeScreenStage();

        ApplicationContext applicationContext = ApplicationContext.getInstance();
        EmployeeModel loggedInEmployee = applicationContext.getLoggedInEmployee();

        loggedInTextField.setText(loggedInEmployee.getName());
        idTextField.setText(loggedInEmployee.getId());

        pickupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.PICKUP);
                customerInfoStage.stage(pickupButton);
            }
        });

        deliveryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.DELIVERY);
                customerInfoStage.stage(deliveryButton);
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.NONE);
                homeScreenStage.stage(backButton);
            }
        });

    }
}
