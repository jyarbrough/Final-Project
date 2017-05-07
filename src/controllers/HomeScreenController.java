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
import stages.OpenOrdersStage;
import stages.PickupDeliveryStage;
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
    public Text openOrdersTitleText;
    public Text newOrderTitleText;
    public ImageView openOrdersIcon;
    public ImageView customerPickupIcon;
    public Text customerPickupTitleText;
    public ImageView newOrderIcon;
    public Pane alertPane;
    public Button noButton;
    public Button yesButton;
    public AnchorPane mainPane;
    public ImageView deliveryIcon;
    public Text deliveriesTitleText;
    public ImageView registerIcon;
    public Text registerText;
    SetAllStages setAllStages = new SetAllStages();
    ApplicationContext applicationContext = ApplicationContext.getInstance();


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        EmployeeModel loggedInEmployee = applicationContext.getLoggedInEmployee();
        loggedInTextField.setText(loggedInEmployee.getName());
        idTextField.setText(loggedInEmployee.getId());

        displayDateAndTime();
        loggedInTextField.setText(loggedInEmployee.getName());
        idTextField.setText(loggedInEmployee.getId());
        logOutHandler();
        initializeButtons();
    }

    private void initializeButtons() {
        newOrderHandler();
        openOrdersHandler();
        pickupHandler();
        deliveryHandler();
        reviseHandler();
        registerHandler();
    }

    private void registerHandler() {
        openRegisterButton.setOnMouseClicked(getMouseEventEventHandler(openRegisterButton,"cash-register"));
        registerIcon.setOnMouseClicked(getMouseEventEventHandler(openRegisterButton,"cash-register"));
        registerText.setOnMouseClicked(getMouseEventEventHandler(openRegisterButton,"cash-register"));
    }

    private void newOrderHandler() {
        newOrderButton.setOnMouseClicked(getMouseEventEventHandler(newOrderButton,"pickup-delivery"));
        newOrderIcon.setOnMouseClicked(getMouseEventEventHandler(newOrderButton,"pickup-delivery"));
        newOrderTitleText.setOnMouseClicked(getMouseEventEventHandler(newOrderButton,"pickup-delivery"));

    }

    private void reviseHandler() {
        //NEED TO MIMIC THIS FOR THE MANAGER FUNCTIONS BUTTON
        reviseOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationContext.getInstance().setOperationMode(OperationMode.MANAGER);
                OpenOrdersStage openOrdersStage = new OpenOrdersStage();
                openOrdersStage.stage(reviseOrderButton);
            }
        });
    }

    private void deliveryHandler() {
        //TOGGLE BUTTONS
        deliveryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationContext.getInstance().setOperationMode(OperationMode.DELIVERY);
                OpenOrdersStage openOrdersStage = new OpenOrdersStage();
                openOrdersStage.stage(deliveryButton);
            }
        });
    }

    private void pickupHandler() {
        pickupButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationContext.getInstance().setOperationMode(OperationMode.PICKUP);
                OpenOrdersStage openOrdersStage = new OpenOrdersStage();
                openOrdersStage.stage(pickupButton);
            }
        });
    }

    private void openOrdersHandler() {
        openOrdersButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ApplicationContext.getInstance().setOperationMode(OperationMode.NONE);
                OpenOrdersStage openOrdersStage = new OpenOrdersStage();
                openOrdersStage.stage(openOrdersButton);
            }
        });
    }



    private void displayDateAndTime() {

        TimeModel timeModel = new TimeModel();
        dayOfTheWeekField.setText(timeModel.getDayOfTheWeek());
        timeField.setText(timeModel.getCurrentTime());
    }

    private EventHandler<MouseEvent> getMouseEventEventHandler(Button button ,String stagePath) {
        return new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setAllStages.stageByButton(button,stagePath);
            }
        };
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
        yesButton.setOnMouseClicked(getMouseEventEventHandler(yesButton,"log-in-screen"));

        noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainPane.setOpacity(1);
                alertPane.setVisible(false);
            }
        });
    }
}








//
//
//
//
//    private void reviseHandler() {
//        //NEED TO MIMIC THIS FOR THE MANAGER FUNCTIONS BUTTON
//        reviseOrderButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                ApplicationContext.getInstance().setOperationMode(OperationMode.MANAGER);
//                OpenOrdersStage openOrdersStage = new OpenOrdersStage();
//                openOrdersStage.stage(reviseOrderButton);
//            }
//        });
//    }
//
//    private void deliveryHandler() {
//        //TOGGLE BUTTONS
//        deliveryButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                ApplicationContext.getInstance().setOperationMode(OperationMode.DELIVERY);
//                OpenOrdersStage openOrdersStage = new OpenOrdersStage();
//                openOrdersStage.stage(deliveryButton);
//            }
//        });
//    }
//
//    private void pickupHandler() {
//        pickupButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                ApplicationContext.getInstance().setOperationMode(OperationMode.PICKUP);
//                OpenOrdersStage openOrdersStage = new OpenOrdersStage();
//                openOrdersStage.stage(pickupButton);
//            }
//        });
//    }
//
//    private void openOrdersHandler() {
//        openOrdersButton.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                ApplicationContext.getInstance().setOperationMode(OperationMode.NONE);
//                OpenOrdersStage openOrdersStage = new OpenOrdersStage();
//                openOrdersStage.stage(openOrdersButton);
//            }
//        });
//    }
//
