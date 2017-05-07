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
import javafx.scene.text.Text;
import models.EmployeeModel;
import models.TimeModel;
import stages.SetAllStages;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class PickupOrDeliveryController implements Initializable {

    public Button pickupButton;
    public Button backButton;
    public TextField loggedInTextField;
    public TextField idTextField;
    public Button deliveryButton;
    public Text deliveryIconTitle;
    public Text pickupIconTitle;
    public Text goBackIconTitle;
    public ImageView deliveryIcon;
    public ImageView pickupIcon;
    public ImageView goBackIcon;
    public TextField dayOfTheWeekField;
    public TextField timeField;
    public Button logOutButton;
    public ImageView logOutIcon;
    public Pane alertPane;
    public Pane mainPane;
    public Button noButton;
    public Button yesButton;

    SetAllStages setAllStages = new SetAllStages();


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SetAllStages setAllStages = new SetAllStages();
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        EmployeeModel loggedInEmployee = applicationContext.getLoggedInEmployee();
        loggedInTextField.setText(loggedInEmployee.getName());
        idTextField.setText(loggedInEmployee.getId());

        displayDateAndTime();
        iconClickHandlers(setAllStages, applicationContext);
        buttonHandlers(setAllStages, applicationContext);
        logOutHandler();

    }

    private void displayDateAndTime() {

        TimeModel timeModel = new TimeModel();
        dayOfTheWeekField.setText(timeModel.getDayOfTheWeek());
        timeField.setText(timeModel.getCurrentTime());
    }

    private void iconClickHandlers(final SetAllStages setAllStages, final ApplicationContext applicationContext) {

        EventHandler<MouseEvent> customerInfoStage = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                applicationContext.setOperationMode(OperationMode.DELIVERY);
                setAllStages.stageByButton(deliveryButton, "customer-info");
            }
        };

        deliveryIconTitle.setOnMouseClicked(customerInfoStage);
        deliveryIcon.setOnMouseClicked(customerInfoStage);
        pickupIcon.setOnMouseClicked(customerInfoStage);
        pickupIconTitle.setOnMouseClicked(customerInfoStage);

        EventHandler<MouseEvent> homeScreenStage = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                applicationContext.setOperationMode(OperationMode.NONE);
                setAllStages.stageByButton(backButton, "home-screen");
            }
        };

        goBackIcon.setOnMouseClicked(homeScreenStage);
        goBackIconTitle.setOnMouseClicked(homeScreenStage);

    }

    private void logOutHandler() {

        EventHandler<MouseEvent> value = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logOutAlertMessage();
            }
        };

        logOutButton.setOnMouseClicked(value);
        logOutIcon.setOnMouseClicked(value);
    }

    private void logOutAlertMessage() {

        alertPane.setVisible(true);
        mainPane.setOpacity(0.30);

        yesButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                setAllStages.stageByButton(logOutButton, "log-in-screen");
            }
        });

        noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainPane.setOpacity(1);
                alertPane.setVisible(false);
            }
        });
    }


    private void buttonHandlers(final SetAllStages setAllStages, final ApplicationContext applicationContext) {

        pickupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.PICKUP);
                setAllStages.stageByButton(pickupButton, "customer-info");
            }
        });

        deliveryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.DELIVERY);
                setAllStages.stageByButton(deliveryButton, "customer-info");
            }
        });

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.NONE);
                setAllStages.stageByButton(backButton, "home-screen");
            }
        });
    }
}
