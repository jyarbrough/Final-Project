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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Callback;
import models.FoodItemModel;
import models.ReceiptModel;
import stages.HomeScreenStage;

import java.net.URL;
import java.util.*;

public class OpenOrdersController implements Initializable {

    public Button backButton;
    public TextField loggedInTextField;
    public TextField idTextField;
    public ImageView goBackIcon;
    public Text goBackIconTitle;

    @FXML
    TableView<ReceiptModel> openOrdersTable = new TableView<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ApplicationContext applicationContext = ApplicationContext.getInstance();

        ObservableList<ReceiptModel> receiptsToDisplay = FXCollections.observableArrayList();

        HashMap<Integer, ReceiptModel> receipts = applicationContext.getReceipts();

        Collection<ReceiptModel> allReceipts = receipts.values();
        ArrayList<ReceiptModel> tempReceiptsToDisplay = new ArrayList<>();

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
                    if (receipt.isOpen() == true) {
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
        receiptsToDisplay.setAll(tempReceiptsToDisplay);

        openOrdersTable.setItems(receiptsToDisplay);

        setupTableColumns();

        backButtonHandler();
    }

    private void backButtonHandler() {
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HomeScreenStage homeScreenStage = new HomeScreenStage();
                homeScreenStage.stage(backButton);
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
