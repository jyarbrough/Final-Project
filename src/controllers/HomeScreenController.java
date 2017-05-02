package controllers;

import contexts.EmployeeContext;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    public Button backButton;

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
                Stage stage;
                Parent root = null;

                stage = (Stage) newOrderButton.getScene().getWindow();

//                EmployeeContext employeeContext = EmployeeContext.getInstance();
//                employeeContext.setEmployeeLoggedInName(employeeName);
//                employeeContext.setEmployeeId(employeeId);

                try {
                    root = FXMLLoader.load(getClass().getResource("../views/pickup-delivery.fxml"));
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
