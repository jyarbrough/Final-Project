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
import models.FoodItemModel;
import models.OrderModel;
import models.TimeModel;
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
    public Button yesButton;
    public Button noButton;
    public Pane alertBackground;
    public AnchorPane mainPage;

    public TextField amountReceived;
    public Label amountDueLabel;
    public Label changeDue;
    public Pane checkoutAlertPane;
    public Button closeOrder;
    SetAllStages setAllStages = new SetAllStages();
    ApplicationContext applicationContext = ApplicationContext.getInstance();

    @FXML
    TableView<OrderModel> openOrdersTable = new TableView<>();
    @FXML
    TableView checkoutReceiptTable = new TableView<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loggedInTextField.setText(ApplicationContext.getInstance().getLoggedInEmployee().getName());
        openOrdersTable.setItems(fetchLatestOpenOrders());

        setupTableColumns();
        backButtonHandler();
        displayDateAndTime();
        logOutHandler();
        checkOutButtonHandler();
        checkoutAlertPane.setVisible(false);
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
                setAllStages.stageByButton(logOutButton, "log-in-screen");
            }
        });

        noButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mainPage.setOpacity(1);
                alertBackground.setVisible(false);
            }
        });
    }

    private void checkOutButtonHandler() {
        checkOutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                setupCheckoutColumns();
                OrderModel selectedItem = openOrdersTable.getSelectionModel().getSelectedItem();

                Double grandTotal = selectedItem.getGrandTotal();
                amountDueLabel.setText(grandTotal.toString());

                checkoutAlertPane.setVisible(true);

//                ArrayList<OrderModel> tempReceiptToDisplay = new ArrayList<>();
//                ArrayList<FoodItemModel> foodItems = selectedItem.getFoodItems();
//                for (FoodItemModel foodItem : foodItems) {
//                    String name = foodItem.getName();
//                    tempReceiptToDisplay.add();
//                }
//                ObservableList<OrderModel> receiptsToDisplay = FXCollections.observableArrayList(tempReceiptToDisplay);
//                checkoutReceiptTable.setItems(receiptsToDisplay);
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

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                applicationContext.setOperationMode(OperationMode.NONE);
                setAllStages.stageByButton(backButton, "home-screen");
            }
        });

        EventHandler<MouseEvent> value = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                applicationContext.setOperationMode(OperationMode.NONE);
                setAllStages.stageByButton(backButton, "home-screen");
            }
        };
        goBackIcon.setOnMouseClicked(value);
        goBackIconTitle.setOnMouseClicked(value);
    }

    private void setupTableColumns() {

        TableColumn ticketNumberColumn = new TableColumn("Ticket #");
        ticketNumberColumn.setMinWidth(75);
        ticketNumberColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                                                   @Override
                                                   public ObservableValue call(TableColumn.CellDataFeatures r) {
                                                       OrderModel receipt = (OrderModel) r.getValue();
                                                       return new SimpleStringProperty(receipt.getTicketNumber().toString());
                                                   }
                                               }
        );

        TableColumn orderTypeColumn = new TableColumn("Order Type");
        orderTypeColumn.setMinWidth(141);
        orderTypeColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                                                @Override
                                                public ObservableValue call(TableColumn.CellDataFeatures r) {
                                                    OrderModel receipt = (OrderModel) r.getValue();
                                                    return new SimpleStringProperty(receipt.getOperationMode().toString());
                                                }
                                            }
        );

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
        totalColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                                            @Override
                                            public ObservableValue call(TableColumn.CellDataFeatures r) {
                                                OrderModel receipt = (OrderModel) r.getValue();
                                                return new SimpleStringProperty(receipt.getGrandTotal().toString());
                                            }
                                        }
        );

        openOrdersTable.getColumns().addAll(ticketNumberColumn, orderTypeColumn, customerNameColumn, totalColumn);
    }

    private void setupCheckoutColumns() {

        TableColumn ticketNumberColumn = new TableColumn("Ticket #");
        ticketNumberColumn.setMinWidth(75);
        ticketNumberColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                                                   @Override
                                                   public ObservableValue call(TableColumn.CellDataFeatures r) {
                                                       OrderModel receipt = (OrderModel) r.getValue();
                                                       return new SimpleStringProperty(receipt.getTicketNumber().toString());
                                                   }
                                               }
        );

        TableColumn customerNameColumn = new TableColumn("Items");
        customerNameColumn.setMinWidth(155);
        customerNameColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                                                   @Override
                                                   public ObservableValue call(TableColumn.CellDataFeatures r) {
                                                       OrderModel receipt = (OrderModel) r.getValue();
                                                       return new SimpleStringProperty(receipt.getFoodItems().toString());
                                                   }
                                               }
        );

        TableColumn totalColumn = new TableColumn("Total");
        totalColumn.setMinWidth(108);
        totalColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures, ObservableValue>() {
                                            @Override
                                            public ObservableValue call(TableColumn.CellDataFeatures r) {
                                                OrderModel receipt = (OrderModel) r.getValue();
                                                return new SimpleStringProperty(receipt.getGrandTotal().toString());
                                            }
                                        }
        );


        checkoutReceiptTable.getColumns().addAll(ticketNumberColumn, customerNameColumn, totalColumn);

    }
}
