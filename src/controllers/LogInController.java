package controllers;

import contexts.ApplicationContext;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import models.EmployeeModel;
import models.TimeModel;
import services.EmployeeService;
import stages.SetAllStages;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LogInController implements Initializable {

    public TextField dayOfTheWeekField;
    public TextField timeField;
    public Pane welcomeBackground;
    public Button welcomeButton;
    public Pane numberPane;
    public Pane wrongPasswordPane;
    public Button wrongPasswordButton;
    public Text wrongPasswordMessage;
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
        initializeNumberPad();
        verifyEmployee();
        displayDateAndTime();
        limitLogInTextField();
    }

    private void limitLogInTextField() {

        logInTextField.textProperty().addListener(
                (observable,oldValue,newValue)-> {
                    if(newValue.length() > 4) logInTextField.setText(oldValue);
                }
        );
    }

    private void displayDateAndTime() {

        TimeModel timeModel = new TimeModel();
        dayOfTheWeekField.setText(timeModel.getDayOfTheWeek());
        timeField.setText(timeModel.getCurrentTime());
    }

    private void initializeNumberPad() {

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

    private void clearTextField() {

        enteredDigits = "";
        logInTextField.clear();
    }

    private void verifyEmployee() {

        SetAllStages setAllStages = new SetAllStages();

        logInButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                ApplicationContext applicationContext = ApplicationContext.getInstance();
                String codeEntered = logInTextField.getText();
                EmployeeService employeeService = new EmployeeService();
                HashMap<String, EmployeeModel> employees = employeeService.get();
                EmployeeModel foundEmployee = employees.get(codeEntered);

                if (foundEmployee != null) {
                    applicationContext.setLoggedInEmployee(foundEmployee);

                    welcomeBackground.setVisible(true);
                    numberPane.setVisible(false);

                    welcomeButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            setAllStages.stageByButton(welcomeButton, "home-screen");
                        }
                    });
                } else {
                    wrongPasswordPane.setVisible(true);
                    numberPane.setVisible(false);
                    initializeWrongPasswordButton();
                }
            }
        });
    }

    private void initializeWrongPasswordButton() {
        wrongPasswordButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearTextField();
                wrongPasswordPane.setVisible(false);
                numberPane.setVisible(true);
            }
        });
    }
}
