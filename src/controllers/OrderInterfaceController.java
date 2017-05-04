package controllers;

import contexts.ApplicationContext;
import enums.OperationMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import models.*;
import services.CategoriesService;
import services.FoodItemsService;
import stages.HomeScreenStage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class OrderInterfaceController implements Initializable {

    @FXML
    public TilePane foodItemPane;
    public TextField totalField;
    public TextField taxField;
    public TableView<FoodItemModel> receiptTableView;
    public TilePane categoryPane;
    public TextField employeeIdField;
    public TextField employeeNameField;
    public TextField subTotalField;
    public TextField ticketNumberField;
    public TextField customerNameField;
    public Button sendButton;
    public Button deleteButton;
    public Button backButton;
    public CheckBox pickupCheckbox;
    public CheckBox deliveryCheckbox;
    public TextField itemCounterField;

    private Integer itemCount = 0;
    private ArrayList<FoodItemModel> itemsOnReceipt = new ArrayList<>();
    private ReceiptModel receipt = new ReceiptModel();
    private ObservableList<FoodItemModel> selectedFoodItemsToDisplay = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CategoriesService categoriesService = new CategoriesService();

        HashMap<String, CategoryModel> categoryModelHashMap = categoriesService.get();
        initializeEventListeners(categoryModelHashMap);
        setupReceiptColumns();
        initializeOperationModeBoxes();
        initializeTicketNumber();
        removeItemFromReceipt();
        sendOrder();

        receipt.setCustomer(ApplicationContext.getInstance().getCurrentCustomer());
        receipt.setOperationMode(ApplicationContext.getInstance().getOperationMode());
    }

    private void initializeTicketNumber() {

        if (receipt.getTicketNumber() == null) {
            ApplicationContext applicationContext = ApplicationContext.getInstance();
            int ticketNumber = applicationContext.getReceipts().size() + 1;
            receipt.setTicketNumber(ticketNumber);
        }
        ticketNumberField.setText(String.valueOf(receipt.getTicketNumber()));
    }

    private void initializeEventListeners(HashMap<String, CategoryModel> categoryModelHashMap) {

        initializeCategoryPane(categoryModelHashMap);
        setUserDisplays();
    }

    private void initializeOperationModeBoxes() {

        ApplicationContext applicationContext = ApplicationContext.getInstance();
        OperationMode operationMode = applicationContext.getOperationMode();

        switch (operationMode) {
            case DELIVERY:
                deliveryCheckbox.setSelected(true);
                pickupCheckbox.setSelected(false);
                break;
            case PICKUP:
                pickupCheckbox.setSelected(true);
                deliveryCheckbox.setSelected(false);
            default:
                break;
        }
    }

    private void setUserDisplays() {

        ApplicationContext applicationContext = ApplicationContext.getInstance();

        CustomerModel customerModel = applicationContext.getCurrentCustomer();
        EmployeeModel loggedInEmployee = applicationContext.getLoggedInEmployee();

        customerNameField.setText(customerModel.getFirstName() + " " + customerModel.getLastName());
        employeeIdField.setText(loggedInEmployee.getId());
        employeeNameField.setText(loggedInEmployee.getName());

    }

    private void initializeCategoryPane(HashMap<String, CategoryModel> categoryModelHashMap) {

        categoryPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                for (CategoryModel categoryModel : categoryModelHashMap.values()) {
                    final Button tempButton = new Button(categoryModel.getName());
                    tempButton.setMinHeight(69);
                    tempButton.setMinWidth(150);

                    categoryPane.getChildren().addAll(tempButton);
                    tempButton.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            String selectedCategory = tempButton.getText();

                            foodItemPane.getChildren().clear();
                            populateItemsInterface(selectedCategory, categoryModelHashMap);
                        }
                    });
                }
            }
        });
    }

    private void populateItemsInterface(String selectedCategory, HashMap<String, CategoryModel> categoryModelHashMap) {

        FoodItemsService foodItemsService = new FoodItemsService();
        ArrayList<FoodItemModel> foodItemModelArrayList = foodItemsService.get(selectedCategory);

        for (FoodItemModel foodItemModel : foodItemModelArrayList) {

            final Button tempButton = new Button(foodItemModel.getName());
            tempButton.setMinHeight(69);
            tempButton.setMinWidth(159);
            foodItemPane.getChildren().addAll(tempButton);

            tempButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String selectedItem = tempButton.getText();

                    addItemToReceipt(selectedItem, selectedCategory, categoryModelHashMap);
                    itemsOnReceipt.add(foodItemModel);
                }
            });
        }
    }

    private void addItemToReceipt(String selectedItem, String categoryName, HashMap<String, CategoryModel> categoryModelHashMap) {

        itemCount++;
        itemCounterField.setText(String.valueOf(itemCount));

        FoodItemsService foodItemsService = new FoodItemsService();
        ArrayList<FoodItemModel> foodItemModelArrayList = foodItemsService.get(categoryName);
        CategoryModel categoryModel = categoryModelHashMap.get(categoryName);

        for (FoodItemModel foodItemModel : foodItemModelArrayList) {
            if (selectedItem.equals(foodItemModel.getName())) {

                FoodItemModel foodItemsList = categoryModel.find(selectedItem);
                selectedFoodItemsToDisplay.add(foodItemsList);

                Double individualItemPrice = Double.valueOf(foodItemModel.getPrice());
                receipt.addItems(individualItemPrice);
            }
        }
        displayTotalsOnReceipt();
    }

    private void removeItemFromReceipt() {
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                itemCount--;
                itemCounterField.setText(String.valueOf(itemCount));

                FoodItemModel itemToRemove = receiptTableView.getSelectionModel().getSelectedItem();
                receiptTableView.getItems().remove(itemToRemove);
                Double priceOfItemToRemove = Double.valueOf(itemToRemove.getPrice());
                receipt.removeItem(priceOfItemToRemove);
                displayTotalsOnReceipt();
            }
        });
    }

    private void displayTotalsOnReceipt() {
        taxField.setText(String.valueOf(receipt.getTax()));
        totalField.setText(String.valueOf(receipt.getGrandTotal()));
        receiptTableView.setItems(selectedFoodItemsToDisplay);
    }

    private void sendOrder() {
        sendButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                receipt.setFoodItems(itemsOnReceipt);

                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.saveReceipt(receipt);

                HomeScreenStage homeScreenStage = new HomeScreenStage();
                homeScreenStage.stage(sendButton);
            }
        });
    }

    private void setupReceiptColumns() {

        TableColumn<FoodItemModel, String> numberColumn = new TableColumn<>("#");
        numberColumn.setMaxWidth(50);
        numberColumn.setCellValueFactory(
                new PropertyValueFactory<>("#"));
        numberColumn.setId("numberColumn");

        TableColumn<FoodItemModel, String> titleColumn = new TableColumn<>("Name");
        titleColumn.setMinWidth(225);
        titleColumn.setCellValueFactory(
                new PropertyValueFactory<>("Name"));
        titleColumn.setId("descriptionColumn");

        TableColumn<FoodItemModel, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(110);
        priceColumn.setCellValueFactory(
                new PropertyValueFactory<>("Price"));
        priceColumn.setId("priceColumn");

        receiptTableView.getColumns().addAll(numberColumn, titleColumn, priceColumn);
    }
}