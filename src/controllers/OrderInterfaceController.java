package controllers;

import contexts.ApplicationContext;
import enums.OperationMode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import models.*;
import services.CategoriesService;
import services.FoodItemsService;
import stages.SetAllStages;

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
    public TextField dayOfTheWeekField;
    public TextField timeField;

    public TilePane pizzaSizesPane;
    public TilePane pizzaItemsPane;
    public Pane sendOrderAlertPane;
    public Button yesButton;
    public Button noButton;
    public AnchorPane mainAnchor;

    private Integer itemCount = 0;
    private ArrayList<FoodItemModel> itemsOnReceipt = new ArrayList<>();
    private OrderModel receipt = new OrderModel();
    private ObservableList<FoodItemModel> selectedFoodItemsToDisplay = FXCollections.observableArrayList();
    SetAllStages setAllStages = new SetAllStages();
    ApplicationContext applicationContext = ApplicationContext.getInstance();

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

        deleteButton.setDisable(true);
        sendButton.setDisable(true);

        receipt.setCustomer(ApplicationContext.getInstance().getCurrentCustomer());
        receipt.setOperationMode(ApplicationContext.getInstance().getOperationMode());

        displayDateAndTime();
    }

    private void initializeTicketNumber() {
        if (receipt.getTicketNumber() == null) {
            ApplicationContext applicationContext = ApplicationContext.getInstance();
            int ticketNumber = applicationContext.getReceipts().size() + 1;
            receipt.setTicketNumber(ticketNumber);
        }
        ticketNumberField.setText(String.valueOf(receipt.getTicketNumber()));
    }

    private void displayDateAndTime() {
        TimeModel timeModel = new TimeModel();
        dayOfTheWeekField.setText(timeModel.getDayOfTheWeek());
        timeField.setText(timeModel.getCurrentTime());
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
                    pizzaItemsPane.getChildren().clear();
                    pizzaSizesPane.getChildren().clear();
                    populateItemsInterface(selectedCategory, categoryModelHashMap);
                }
            });
        }
    }

    private void populateItemsInterface(String selectedCategory, HashMap<String, CategoryModel> categoryModelHashMap) {
        FoodItemsService foodItemsService = new FoodItemsService();
        ArrayList<FoodItemModel> foodItemModelArrayList = foodItemsService.get(selectedCategory);

        for (FoodItemModel foodItemModel : foodItemModelArrayList) {
            if (selectedCategory.equals("Pizza")) {
                final Button pizzaSizes = setUpPizzaButtons(foodItemModel);
                pizzaSizesPane.getChildren().addAll(pizzaSizes);
                tempButtonAction(selectedCategory, categoryModelHashMap, foodItemModel, pizzaSizes);
            } else {
                final Button tempButton = setUpItemsInterface(foodItemModel);
                foodItemPane.getChildren().addAll(tempButton);
                tempButtonAction(selectedCategory, categoryModelHashMap, foodItemModel, tempButton);
            }
        }
    }

    private Button setUpItemsInterface(FoodItemModel foodItemModel) {
        final Button tempButton = new Button(foodItemModel.getName());
        pizzaSizesPane.getChildren().clear();
        pizzaSizesPane.setVisible(false);
        pizzaItemsPane.setVisible(false);
        pizzaItemsPane.setDisable(true);
        pizzaSizesPane.setDisable(true);
        tempButton.setMinHeight(60);
        tempButton.setMaxHeight(60);
        tempButton.setMaxWidth(140);
        tempButton.setMinWidth(140);
        tempButton.setWrapText(true);
        tempButton.setAlignment(Pos.CENTER);
        return tempButton;
    }

    private Button setUpPizzaButtons(FoodItemModel foodItemModel) {
        final Button pizzaButton = new Button(foodItemModel.getName());
        pizzaItemsPane.setDisable(false);
        pizzaItemsPane.setVisible(true);
        pizzaSizesPane.setDisable(false);
        pizzaSizesPane.setVisible(true);
        pizzaButton.setMinHeight(80);
        pizzaButton.setMaxHeight(80);
        pizzaButton.setMaxWidth(105);
        pizzaButton.setMinWidth(105);
        pizzaButton.setWrapText(true);
        pizzaButton.setAlignment(Pos.CENTER);
        return pizzaButton;
    }

    private void tempButtonAction(final String selectedCategory, final HashMap<String, CategoryModel> categoryModelHashMap, final FoodItemModel foodItemModel, final Button tempButton) {
        tempButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String selectedItem = tempButton.getText();

                if (selectedCategory.equals("Pizza")) {

                    pizzaItemsPane.getChildren().clear();
                    getPizzaToppings(selectedItem, selectedCategory, categoryModelHashMap, foodItemModel);
                } else {
                    addItemToReceipt(selectedItem, selectedCategory, categoryModelHashMap);
                    itemsOnReceipt.add(foodItemModel);
                }
                sendButton.setDisable(false);
            }
        });
    }

    private void getPizzaToppings(String selectedItem, String selectedCategory, HashMap<String, CategoryModel> categoryModelHashMap, FoodItemModel foodItemModel) {
        FoodItemsService foodItemsService = new FoodItemsService();
        ArrayList<FoodItemModel> foodItemModelArrayList = foodItemsService.get(selectedItem);

        for (FoodItemModel pizzaToppingsModel : foodItemModelArrayList) {
            final Button pizzaToppings = new Button(pizzaToppingsModel.getName());
            pizzaToppings.setMinHeight(60);
            pizzaToppings.setMaxHeight(60);
            pizzaToppings.setMaxWidth(100);
            pizzaToppings.setMinWidth(100);
            pizzaToppings.setWrapText(true);
            pizzaToppings.setAlignment(Pos.CENTER);

            pizzaItemsPane.getChildren().addAll(pizzaToppings);
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
                if (selectedFoodItemsToDisplay.isEmpty()) {
                    deleteButton.setDisable(true);
                    sendButton.setDisable(true);

                    itemCount = 0;
                    itemCounterField.setText(String.valueOf(itemCount));
                } else {
                    itemCount--;
                    itemCounterField.setText(String.valueOf(itemCount));

                    FoodItemModel itemToRemove = receiptTableView.getSelectionModel().getSelectedItem();
                    receiptTableView.getItems().remove(itemToRemove);
                    Double priceOfItemToRemove = Double.valueOf(itemToRemove.getPrice());
                    receipt.removeItem(priceOfItemToRemove);
                    displayTotalsOnReceipt();
                }
            }
        });
        receiptTableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                deleteButton.setDisable(false);
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
                sendAlert();
            }
        });
    }

    private void sendAlert() {
        sendOrderAlertPane.setVisible(true);
        mainAnchor.setOpacity(0.30);

        yesButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                receipt.setFoodItems(itemsOnReceipt);
//                ApplicationContext applicationContext = ApplicationContext.getInstance();
                applicationContext.saveReceipt(receipt);
                setAllStages.stageByButton(sendButton, "home-screen");
            }
        });

        noButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainAnchor.setOpacity(1);
                sendOrderAlertPane.setVisible(false);
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
