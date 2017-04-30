package controllers;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.CustomerModel;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CustomerModel customerModel = new CustomerModel();

        placeOrderButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                customerModel.setFirstName(firstNameField.getText());
                customerModel.setLastName(lastNameField.getText());
                customerModel.setAddressOne(addressOneField.getText());
                customerModel.setAddressTwo(addressTwoField.getText());
                customerModel.setCity(cityField.getText());
                customerModel.setZipCode(zipCodeField.getText());
                customerModel.setState(stateField.getText());
                customerModel.setPhoneNumber(phoneNumberField.getText());

                System.out.println(customerModel.getFirstName());
                System.out.println(customerModel.getLastName());

            }
        });
    }
}
