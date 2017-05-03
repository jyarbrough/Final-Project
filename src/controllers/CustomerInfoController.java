package controllers;

import contexts.CustomerContext;
import contexts.EmployeeContext;
import contexts.PickupOrDeliveryContext;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.CustomerModel;
import models.TimeModel;
import stages.MainInterfaceStage;
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
    public CheckBox deliveryCheckbox;
    public CheckBox pickupCheckbox;
    public TextField timeField;
    public TextField dayOfTheWeekField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        displayPickupOrDelivery();
        displayEmployeeName();
        placeOrderActionHandler();
        backButtonAction();
        displayDateAndTime();
    }

    private void placeOrderActionHandler() {

        placeOrderButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                CustomerContext customerContext = CustomerContext.getInstance();
                MainInterfaceStage mainInterfaceStage = new MainInterfaceStage();
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
                customerContext.setCustomerModel(customerModel);
                mainInterfaceStage.stage(placeOrderButton);
//                getCustomerInfo(customerProfile);
            }
        });
    }

    private void displayDateAndTime() {

        TimeModel timeModel = new TimeModel();
        dayOfTheWeekField.setText(timeModel.getDayOfTheWeek());
        timeField.setText(timeModel.getCurrentTime());
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
            pickupCheckbox.setSelected(true);
            disableButtons();
        } else {
            deliveryCheckbox.setSelected(true);
        }
    }

    private void disableButtons() {

        addressOneField.setDisable(true);
        addressTwoField.setDisable(true);
        cityField.setDisable(true);
        zipCodeField.setDisable(true);
        stateField.setDisable(true);
    }
}
