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

import java.io.IOException;
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

        loggedInTextField.setText(employeeName);
        idTextField.setText(employeeId);

        pickupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pickupOrDeliveryContext.setPickup(true);
                pickupOrDeliveryContext.setDelivery(false);

                Stage stage;
                Parent root = null;

                stage = (Stage) pickupButton.getScene().getWindow();

                try {
                    root = FXMLLoader.load(getClass().getResource("../views/customer-info.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root, 1300, 900);
                stage.setScene(scene);
                stage.show();
            }
        });

        deliveryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pickupOrDeliveryContext.setDelivery(true);
                pickupOrDeliveryContext.setPickup(false);

                Stage stage;
                Parent root = null;

                stage = (Stage) pickupButton.getScene().getWindow();

                try {
                    root = FXMLLoader.load(getClass().getResource("../views/customer-info.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Scene scene = new Scene(root, 1300, 900);
                stage.setScene(scene);
                stage.show();
            }
        });

    }
}
