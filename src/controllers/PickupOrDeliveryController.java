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
    public Pane alertBackground;
    public AnchorPane pickupDeliveryAnchor;


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

        deliveryIconTitle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.DELIVERY);
                setAllStages.stageByButton(deliveryButton, "customer-info");
            }
        });

        deliveryIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.DELIVERY);
                setAllStages.stageByButton(deliveryButton, "customer-info");
            }
        });

        pickupIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.PICKUP);
                setAllStages.stageByButton(pickupButton, "customer-info");
            }
        });

        pickupIconTitle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.PICKUP);
                setAllStages.stageByButton(pickupButton, "customer-info");
            }
        });

        goBackIcon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.NONE);
                setAllStages.stageByButton(backButton, "home-screen");
            }
        });

        goBackIconTitle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.setOperationMode(OperationMode.NONE);
                setAllStages.stageByButton(backButton, "home-screen");
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
        pickupDeliveryAnchor.setOpacity(0.30);

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
                SetAllStages setAllStages = new SetAllStages();
                setAllStages.stageByButton(logOutButton, "pickup-delivery");
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
