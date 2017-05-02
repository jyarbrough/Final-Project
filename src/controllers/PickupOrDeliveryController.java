package controllers;

import contexts.EmployeeContext;
import contexts.PickupOrDeliveryContext;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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

        PickupOrDeliveryContext pickupOrDeliveryContext = PickupOrDeliveryContext.getInstance();
        EmployeeContext employeeContext = EmployeeContext.getInstance();
        String employeeName = employeeContext.getEmployeeLoggedInName();
        String employeeId = employeeContext.getEmployeeId();
        CustomerInfoStage customerInfoStage = new CustomerInfoStage();
        HomeScreenStage homeScreenStage = new HomeScreenStage();

        loggedInTextField.setText(employeeName);
        idTextField.setText(employeeId);

        pickupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                pickupOrDeliveryContext.setPickup(true);
                pickupOrDeliveryContext.setDelivery(false);
                customerInfoStage.stage(pickupButton);
            }
        });

        deliveryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pickupOrDeliveryContext.setDelivery(true);
                pickupOrDeliveryContext.setPickup(false);
                customerInfoStage.stage(deliveryButton);
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pickupOrDeliveryContext.setDelivery(false);
                pickupOrDeliveryContext.setPickup(false);
                homeScreenStage.stage(backButton);
            }
        });

    }
}
