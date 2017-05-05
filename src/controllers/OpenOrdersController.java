package controllers;


import contexts.ApplicationContext;
import enums.OperationMode;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import models.EmployeeModel;
import models.FoodItemModel;
import models.ReceiptModel;
import models.TimeModel;
import stages.HomeScreenStage;
import stages.SetAllStages;

import java.net.URL;
import java.util.*;

public class OpenOrdersController implements Initializable {

    public Button backButton;
    public TextField loggedInTextField;
    public ImageView goBackIcon;
    public Text goBackIconTitle;

    public Button checkOutButton;
    public RadioButton deliveriesRadioButton;
    public RadioButton pickupRadioButton;
    public TextField timeField;
    public TextField dayOfTheWeekField;
    public ToggleGroup pickUpOrDeliveryRadioGroup;
    public Button logOutButton;
    public ImageView logOutIcon;
    public AnchorPane alertPane;
    public Button yesButton;
    public Button noButton;
    public Pane alertBackground;
    public AnchorPane mainPage;

    @FXML
    TableView<ReceiptModel> openOrdersTable = new TableView<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ApplicationContext applicationContext = ApplicationContext.getInstance();
        ObservableList<ReceiptModel> receiptsToDisplay = FXCollections.observableArrayList();
        HashMap<Integer, ReceiptModel> receipts = applicationContext.getReceipts();
        Collection<ReceiptModel> allReceipts = receipts.values();
        EmployeeModel loggedInEmployee = applicationContext.getLoggedInEmployee();
        setupTableColumns();
        ArrayList<ReceiptModel> tempReceiptsToDisplay = new ArrayList<>();
        loggedInTextField.setText(loggedInEmployee.getName());
        receiptsToDisplay.setAll(tempReceiptsToDisplay);
        openOrdersTable.setItems(receiptsToDisplay);

        switch (applicationContext.getOperationMode()) {
            case DELIVERY:
                for (ReceiptModel receipt : allReceipts) {
                    if (receipt.getOperationMode() == OperationMode.DELIVERY) {
                        tempReceiptsToDisplay.add(receipt);
                    }
                }
                break;

            case PICKUP:
                for (ReceiptModel receipt : allReceipts) {
                    if (receipt.getOperationMode() == OperationMode.PICKUP) {
                        tempReceiptsToDisplay.add(receipt);
                    }
                }
                break;

            case NONE:
                for (ReceiptModel receipt : allReceipts) {
                    if (receipt.isOpen()) {
                        tempReceiptsToDisplay.add(receipt);
                    }
                }
                break;

            case MANAGER:
                for (ReceiptModel receiptModel : allReceipts) {
                    tempReceiptsToDisplay.add(receiptModel);
                }
                break;
            default:
                break;

        }


        backButtonHandler();
        displayDateAndTime();
        logOutHandler();
        checkOutButtonHandler();
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
        mainPage.setOpacity(0.30);

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
                setAllStages.stageByButton(logOutButton, "open-orders");
            }
        });
    }


    private void checkOutButtonHandler() {

        checkOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                openOrdersTable.getItems().remove(openOrdersTable.getSelectionModel().getSelectedItem());

            }
        });
    }

    private void displayDateAndTime() {

        TimeModel timeModel = new TimeModel();
        dayOfTheWeekField.setText(timeModel.getDayOfTheWeek());
        timeField.setText(timeModel.getCurrentTime());
    }



    private void backButtonHandler() {

        SetAllStages setAllStages = new SetAllStages();
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HomeScreenStage homeScreenStage = new HomeScreenStage();
                homeScreenStage.stage(backButton);
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

    private void setupTableColumns() {

        TableColumn ticketNumberColumn = new TableColumn("Ticket #");
        ticketNumberColumn.setMinWidth(75);
        ticketNumberColumn.setCellValueFactory(
                new PropertyValueFactory<ReceiptModel, String>("ticketNumber"));

        TableColumn orderTypeColumn = new TableColumn("Order Type");
        orderTypeColumn.setMinWidth(141);
        orderTypeColumn.setCellValueFactory(
                new PropertyValueFactory<ReceiptModel, String>("type"));

        TableColumn customerNameColumn = new TableColumn("Customer Name");
        customerNameColumn.setMinWidth(314);
        customerNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                                                   @Override
                                                   public ObservableValue call(TableColumn.CellDataFeatures r) {
                                                       ReceiptModel receipt = (ReceiptModel) r.getValue();
                                                       return new SimpleStringProperty(receipt.getCustomer().getFirstName());
                                                   }
                                               }
        );

        TableColumn totalColumn = new TableColumn("Total");
        totalColumn.setMinWidth(101);
        totalColumn.setCellValueFactory(
                new PropertyValueFactory<ReceiptModel, StringJoiner>("total"));

        openOrdersTable.getColumns().addAll(ticketNumberColumn, orderTypeColumn, customerNameColumn);
    }
}
