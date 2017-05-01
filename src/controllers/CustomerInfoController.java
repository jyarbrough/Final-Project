package controllers;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import models.CustomerModel;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
//                movingToNextScreen(customerProfile);
            }
        });
    }

    public HashMap<String, CustomerModel> getCustomerInfo(HashMap<String, CustomerModel> customerProfile) {

        return customerProfile;

    }

//    private void movingToNextScreen(HashMap<String, CustomerModel> customerProfile) {
//
//        getCustomerInfo.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//                Stage stage;
//                Parent root = null;
//                stage = (Stage) getCustomerInfo.getScene().getWindow();
//
//                Context context = Context.getInstance();
//
//                context.setCustomerProfile(customerProfile.get());
//
//                try {
//                    root = FXMLLoader.load(getClass().getResource("../Stock-Graph.fxml"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                Scene scene = new Scene(root, 1500, 900);
//                stage.setScene(scene);
//                stage.show();
//
//            }
//        });
//
//    }
}
