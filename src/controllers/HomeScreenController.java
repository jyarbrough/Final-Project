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
import models.EmployeeModel;
import models.TimeModel;
import stages.OpenOrdersStage;
import stages.PickupDeliveryStage;
import stages.SetAllStages;

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
    public TextField timeField;
    public TextField dayOfTheWeekField;
    public Button logOutButton;

    public ImageView logOutIcon;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ApplicationContext applicationContext = ApplicationContext.getInstance();
        EmployeeModel loggedInEmployee = applicationContext.getLoggedInEmployee();
        loggedInTextField.setText(loggedInEmployee.getName());
        idTextField.setText(loggedInEmployee.getId());

        displayDateAndTime();

        if (loggedInEmployee == null) {
            throw new RuntimeException("null employee was set");
        }

        loggedInTextField.setText(loggedInEmployee.getName());
        idTextField.setText(loggedInEmployee.getId());

        newOrderButton.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                PickupDeliveryStage pickupDeliveryStage = new PickupDeliveryStage();
                pickupDeliveryStage.stage(newOrderButton);
            }
        });

        openOrdersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationContext.getInstance().setOperationMode(OperationMode.NONE);
                OpenOrdersStage openOrdersStage = new OpenOrdersStage();
                openOrdersStage.stage(openOrdersButton);
            }
        });

        pickupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationContext.getInstance().setOperationMode(OperationMode.PICKUP);
                OpenOrdersStage openOrdersStage = new OpenOrdersStage();
                openOrdersStage.stage(pickupButton);
            }
        });

        deliveryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationContext.getInstance().setOperationMode(OperationMode.DELIVERY);
                OpenOrdersStage openOrdersStage = new OpenOrdersStage();
                openOrdersStage.stage(deliveryButton);
            }
        });

        reviseOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationContext.getInstance().setOperationMode(OperationMode.MANAGER);
                OpenOrdersStage openOrdersStage = new OpenOrdersStage();
                openOrdersStage.stage(reviseOrderButton);
            }
        });

        logOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SetAllStages setAllStages = new SetAllStages();
                setAllStages.stageByButton(logOutButton, "log-in-screen");
            }
        });

        logOutIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                SetAllStages setAllStages = new SetAllStages();
                setAllStages.stageByButton(logOutButton, "log-in-screen");

            }
        });
    }

    private void displayDateAndTime() {

        TimeModel timeModel = new TimeModel();
        dayOfTheWeekField.setText(timeModel.getDayOfTheWeek());
        timeField.setText(timeModel.getCurrentTime());
    }
}
