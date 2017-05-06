package controllers;


import contexts.ApplicationContext;
import enums.OperationMode;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import models.EmployeeModel;
import models.OrderModel;
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

    public TextField amountReceived;
    public Label amountDueLabel;
    public Label changeDue;
    public Pane checkoutAlertPane;

    public Button closeOrder;

    @FXML
    TableView<OrderModel> openOrdersTable = new TableView<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loggedInTextField.setText(ApplicationContext.getInstance().getLoggedInEmployee().getName());

        openOrdersTable.setItems(fetchLatestOpenOrders());

        setupTableColumns();
        backButtonHandler();
        displayDateAndTime();
        logOutHandler();
        checkOutButtonHandler();
    }

    private ObservableList<OrderModel> fetchLatestOpenOrders() {
        ApplicationContext applicationContext = ApplicationContext.getInstance();
        Collection<OrderModel> allReceipts = applicationContext.getReceipts().values();
        ArrayList<OrderModel> tempReceiptsToDisplay = new ArrayList<>();

        switch (applicationContext.getOperationMode()) {
            case DELIVERY:
                for (OrderModel receipt : allReceipts) {
                    if (receipt.isOpen() && receipt.getOperationMode() == OperationMode.DELIVERY) {
                        tempReceiptsToDisplay.add(receipt);
                    }
                }
                break;

            case PICKUP:
                for (OrderModel receipt : allReceipts) {
                    if (receipt.isOpen() && receipt.getOperationMode() == OperationMode.PICKUP) {
                        tempReceiptsToDisplay.add(receipt);
                    }
                }
                break;

            case NONE:
                for (OrderModel receipt : allReceipts) {
                    if (receipt.isOpen()) {
                        tempReceiptsToDisplay.add(receipt);
                    }
                }
                break;

            case MANAGER:
                for (OrderModel orderModel : allReceipts) {
                    tempReceiptsToDisplay.add(orderModel);
                }
                break;

            default:
                break;

        }

        ObservableList<OrderModel> receiptsToDisplay = FXCollections.observableArrayList(tempReceiptsToDisplay);
        return receiptsToDisplay;
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

                OrderModel selectedItem = openOrdersTable.getSelectionModel().getSelectedItem();

                Double grandTotal = selectedItem.getGrandTotal();
                amountDueLabel.setText(grandTotal.toString());

                checkoutAlertPane.setVisible(true);
            }

        });

        amountReceived.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    OrderModel selectedItem = openOrdersTable.getSelectionModel().getSelectedItem();
                    Double grandTotal = selectedItem.getGrandTotal();

                    Double received = Double.valueOf(amountReceived.getText());
                    Double change = received - grandTotal;
                    changeDue.setText(change.toString());
                }
            }
        });

        closeOrder.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                OrderModel selectedItem = openOrdersTable.getSelectionModel().getSelectedItem();
                selectedItem.setOpen(false);
                openOrdersTable.setItems(fetchLatestOpenOrders());
                checkoutAlertPane.setVisible(false);
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
                new PropertyValueFactory<OrderModel, String>("ticketNumber"));

        TableColumn orderTypeColumn = new TableColumn("Order Type");
        orderTypeColumn.setMinWidth(141);
        orderTypeColumn.setCellValueFactory(
                new PropertyValueFactory<OrderModel, String>("type"));

        TableColumn customerNameColumn = new TableColumn("Customer Name");
        customerNameColumn.setMinWidth(314);
        customerNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                                                   @Override
                                                   public ObservableValue call(TableColumn.CellDataFeatures r) {
                                                       OrderModel receipt = (OrderModel) r.getValue();
                                                       return new SimpleStringProperty(receipt.getCustomer().getFirstName());
                                                   }
                                               }
        );

        TableColumn totalColumn = new TableColumn("Total");
        totalColumn.setMinWidth(101);
        totalColumn.setCellValueFactory(
                new PropertyValueFactory<OrderModel, StringJoiner>("total"));

        openOrdersTable.getColumns().addAll(ticketNumberColumn, orderTypeColumn, customerNameColumn);
    }
}
