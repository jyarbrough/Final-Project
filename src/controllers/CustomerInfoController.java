package controllers;

import contexts.ApplicationContext;
import enums.OperationMode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.CustomerModel;
import models.EmployeeModel;
import models.TimeModel;
import stages.MainInterfaceStage;
import stages.PickupDeliveryStage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import static enums.OperationMode.DELIVERY;

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
                ApplicationContext applicationContext = ApplicationContext.getInstance();
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

                applicationContext.setCurrentCustomer(customerModel);
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

                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.NONE);

                PickupDeliveryStage pickupDeliveryStage = new PickupDeliveryStage();
                pickupDeliveryStage.stage(backButton);
            }
        });
    }

    private void displayEmployeeName() {

        ApplicationContext applicationContext = ApplicationContext.getInstance();
        EmployeeModel loggedInEmployee = applicationContext.getLoggedInEmployee();
        employeeNameField.setText(loggedInEmployee.getName());
    }

    private void displayPickupOrDelivery() {

        ApplicationContext applicationContext = ApplicationContext.getInstance();
        OperationMode operationMode = applicationContext.getOperationMode();

        switch (operationMode) {

            case DELIVERY:
                deliveryCheckbox.setSelected(true);
                pickupCheckbox.setSelected(false);
                break;
            case PICKUP:
                pickupCheckbox.setSelected(true);
                deliveryCheckbox.setSelected(false);
                disableButtons();
            default:
                break;
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
