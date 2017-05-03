package controllers;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import models.FoodItemModel;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringJoiner;

public class OpenOrdersController implements Initializable {

    public Button backButton;
    public TextField loggedInTextField;
    public TextField idTextField;
    public ImageView goBackIcon;
    public Text goBackIconTitle;

    @FXML
    TableView<FoodItemModel> openOrdersTable = new TableView<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {












    }

    private void setupTableColumns() {
        TableColumn ticketNumberColumn = new TableColumn("Ticket #");
        ticketNumberColumn.setMinWidth(75);
        ticketNumberColumn.setCellValueFactory(
                new PropertyValueFactory<FoodItemModel, String>("ticket"));

        TableColumn timeTakenColumn = new TableColumn("Time Taken");
        timeTakenColumn.setMinWidth(92);
        timeTakenColumn.setCellValueFactory(
                new PropertyValueFactory<FoodItemModel, String>("time"));

        TableColumn orderTypeColumn = new TableColumn("Order Type");
        orderTypeColumn.setMinWidth(141);
        orderTypeColumn.setCellValueFactory(
                new PropertyValueFactory<FoodItemModel, String>("type"));

        TableColumn customerNameColumn = new TableColumn("Customer Name");
        customerNameColumn.setMinWidth(314);
        customerNameColumn.setCellValueFactory(
                new PropertyValueFactory<FoodItemModel, String>("Name"));

        TableColumn totalColumn = new TableColumn("Total");
        totalColumn.setMinWidth(101);
        totalColumn.setCellValueFactory(
                new PropertyValueFactory<FoodItemModel, StringJoiner>("total"));

        openOrdersTable.getColumns().addAll(ticketNumberColumn, timeTakenColumn, orderTypeColumn, customerNameColumn);
    }
}
