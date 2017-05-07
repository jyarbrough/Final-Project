package controllers;

import contexts.ApplicationContext;
import enums.OperationMode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import models.EmployeeModel;
import models.TimeModel;
import stages.SetAllStages;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class HomeScreenController implements Initializable {

    public Button newOrderButton;
    public Button pickupButton;
    public Button deliveryButton;
    public Button openOrdersButton;
    public Button reviseOrderButton;
    public Button openRegisterButton;
    public TextField loggedInTextField;
    public TextField idTextField;
    public TextField timeField;
    public TextField dayOfTheWeekField;
    public Button logOutButton;

    public ImageView logOutIcon;
    public Pane alertBackground;
    public Button yesButton;
    public Button noButton;
    public AnchorPane homeScreenAnchor;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ApplicationContext applicationContext = ApplicationContext.getInstance();
        EmployeeModel loggedInEmployee = applicationContext.getLoggedInEmployee();
        SetAllStages setAllStages = new SetAllStages();
        loggedInTextField.setText(loggedInEmployee.getName());
        idTextField.setText(loggedInEmployee.getId());

        displayDateAndTime();
        logOutHandler();

//        if (loggedInEmployee == null) {
//            throw new RuntimeException("null employee was set");
//        }

        loggedInTextField.setText(loggedInEmployee.getName());
        idTextField.setText(loggedInEmployee.getId());

        newOrderButton.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {

                setAllStages.stageByButton(newOrderButton, "pickup-delivery");
            }
        });

        openOrdersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ApplicationContext.getInstance().setOperationMode(OperationMode.NONE);
                setAllStages.stageByButton(openRegisterButton, "open-orders");
            }
        });

        pickupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationContext.getInstance().setOperationMode(OperationMode.PICKUP);
                setAllStages.stageByButton(pickupButton, "open-orders");
            }
        });

        deliveryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationContext.getInstance().setOperationMode(OperationMode.DELIVERY);
                setAllStages.stageByButton(deliveryButton, "open-orders");

            }
        });

        reviseOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ApplicationContext.getInstance().setOperationMode(OperationMode.MANAGER);
                setAllStages.stageByButton(reviseOrderButton, "open-orders");
            }
        });

//        logOutButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//            }
//        });
//
//        logOutIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//
//                SetAllStages setAllStages = new SetAllStages();
//                setAllStages.stageByButton(logOutButton, "log-in-screen");
//
//            }
//        });

        openRegisterButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                SetAllStages setAllStages = new SetAllStages();
                setAllStages.stageByButton(openRegisterButton, "cash-register");
            }
        });
    }

    private void logOutHandler() {

        logOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                logOutAlertMessage();
            }
        });

        logOutIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                logOutAlertMessage();

            }
        });
    }

    private void logOutAlertMessage() {
        alertBackground.setVisible(true);
        homeScreenAnchor.setOpacity(0.30);

        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SetAllStages setAllStages = new SetAllStages();
                setAllStages.stageByButton(logOutButton, "log-in-screen");
            }
        });

        noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                homeScreenAnchor.setOpacity(1);
                alertBackground.setVisible(false);
            }
        });
    }

    private void displayDateAndTime() {

        TimeModel timeModel = new TimeModel();
        dayOfTheWeekField.setText(timeModel.getDayOfTheWeek());
        timeField.setText(timeModel.getCurrentTime());
    }
}
