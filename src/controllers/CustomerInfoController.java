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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.CustomerModel;
import stages.PickupDeliveryStage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class CustomerInfoController implements Initializable {

    public TextField firstNameField;
    public TextField lastNameField;
    public TextField addressOneField;
    public TextField addressTwoField;
    public TextField cityField;
    public TextField zipCodeField;
    public TextField stateField;
    public TextField phoneNumberField;
    public Button placeOrderButton;
    public Button backButton;
    public TextField employeeNameField;
    public TextField pickupOrDeliveryField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        displayPickupOrDelivery();
        displayEmployeeName();
        placeOrderActionHandler();
        backButtonAction();
    }

    private void placeOrderActionHandler() {
        placeOrderButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                HashMap<String, CustomerModel> customerProfile = new HashMap<>();

                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String addressOne = addressOneField.getText();
                String addressTwo = addressTwoField.getText();
                String city = cityField.getText();
                String zipCode = zipCodeField.getText();
                String state = stateField.getText();
                String phoneNumber = phoneNumberField.getText();

                CustomerModel customerModel = new CustomerModel(firstName, lastName, addressOne, addressTwo, phoneNumber, state, zipCode, city);
                customerProfile.put(customerModel.getLastName(), customerModel);
                getCustomerInfo(customerProfile);
            }
        });
    }

    private void backButtonAction() {
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                PickupOrDeliveryContext pickupOrDeliveryContext = PickupOrDeliveryContext.getInstance();
                pickupOrDeliveryContext.setPickup(false);
                pickupOrDeliveryContext.setDelivery(false);
                PickupDeliveryStage pickupDeliveryStage = new PickupDeliveryStage();
                pickupDeliveryStage.stage(backButton);
            }
        });
    }

    private void displayEmployeeName() {
        EmployeeContext employeeContext = EmployeeContext.getInstance();
        String employeeName = employeeContext.getEmployeeLoggedInName();
        employeeNameField.setText(employeeName);
    }

    private void displayPickupOrDelivery() {

        PickupOrDeliveryContext pickupOrDeliveryContext = PickupOrDeliveryContext.getInstance();
        if (pickupOrDeliveryContext.isPickup()) {
            pickupOrDeliveryField.setText("Pick-Up");
            disableButtons();
        } else {
            pickupOrDeliveryField.setText("Delivery");
        }
    }

    public HashMap<String, CustomerModel> getCustomerInfo(HashMap<String, CustomerModel> customerProfile) {
        return customerProfile;
    }

    private void disableButtons() {
        addressOneField.setDisable(true);
        addressTwoField.setDisable(true);
        cityField.setDisable(true);
        zipCodeField.setDisable(true);
        stateField.setDisable(true);
    }
}
