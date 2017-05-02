package controllers;

import contexts.EmployeeContext;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.EmployeeModel;
import services.EmployeeService;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    private String enteredDigits = "";

    public Button numberOne;
    public Button numberTwo;
    public Button numberThree;
    public Button numberFour;
    public Button numberFive;
    public Button numberSix;
    public Button numberSeven;
    public Button numberEight;
    public Button numberNine;
    public Button numberZero;
    public Button clearButton;
    public Button logInButton;
    public TextField logInTextField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Scene scene = new Scene(new Group(), 500, 400);
        scene.getStylesheets().add("stylesheets/posStyles.css");
        initializeButtons();
        verifyEmployee();
    }

    public void initializeButtons() {

        numberOne.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseClick) {
                enteredDigits += "1";
                logInTextField.setText(enteredDigits);
            }
        });

        numberTwo.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                enteredDigits += "2";
                logInTextField.setText(enteredDigits);
            }
        });

        numberThree.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                enteredDigits += "3";
                logInTextField.setText(enteredDigits);
            }
        });

        numberFour.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                enteredDigits += "4";
                logInTextField.setText(enteredDigits);
            }
        });

        numberFive.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                enteredDigits += "5";
                logInTextField.setText(enteredDigits);
            }
        });

        numberSix.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                enteredDigits += "6";
                logInTextField.setText(enteredDigits);
            }
        });

        numberSeven.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                enteredDigits += "7";
                logInTextField.setText(enteredDigits);
            }
        });

        numberEight.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                enteredDigits += "8";
                logInTextField.setText(enteredDigits);
            }
        });

        numberNine.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                enteredDigits += "9";
                logInTextField.setText(enteredDigits);
            }
        });

        numberZero.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                enteredDigits += "0";
                logInTextField.setText(enteredDigits);
            }
        });

        clearButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearTextField();
            }
        });
    }

    public void clearTextField() {
        enteredDigits = "";
        logInTextField.clear();
    }

    public void verifyEmployee() {

        logInButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                String codeEntered = logInTextField.getText();

                EmployeeService employeeService = new EmployeeService();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                HashMap<String, EmployeeModel> employeeModel = employeeService.get();

                for (EmployeeModel model : employeeModel.values()) {

                    if (codeEntered.equals(model.getLogInCode())) {

//                        alert.setTitle("Information");
//                        alert.setHeaderText("Hello " + model.getName());
//                        alert.setContentText("Heres the content");
//                        alert.showAndWait();

                        String employeeName = model.getName();
                        String employeeId = model.getId();

                        Stage stage;
                        Parent root = null;
                        stage = (Stage) logInButton.getScene().getWindow();

                        EmployeeContext employeeContext = EmployeeContext.getInstance();
                        employeeContext.setEmployeeLoggedInName(employeeName);
                        employeeContext.setEmployeeId(employeeId);

                        try {
                            root = FXMLLoader.load(getClass().getResource("../views/home-screen.fxml"));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Scene scene = new Scene(root, 1300, 900);
                        stage.setScene(scene);
                        stage.show();
                    }

                    else {
                        alert.setTitle("Uh Oh!");
                        alert.setHeaderText("Log In Doesn't Exist.");
                        alert.setContentText("Please Try Again.");
                        clearTextField();
                        alert.show();
                    }
                }
            }
        });
    }
}
