package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import models.CategoryModel;
import models.FoodItemModel;
import models.ReceiptModel;
import services.CategoriesService;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private HashMap<String, CategoryModel> categoryModelHashMap;
    private ReceiptModel receipt = new ReceiptModel();

    @FXML
    public ListView categoryListView;
    public ListView foodItemsListView;
    public Pane backPane;
    public TextField totalField;
    public TextField taxField;
    public TableView<FoodItemModel> receiptTableView;
    private ObservableList<FoodItemModel> selectedFoodItemsToDisplay = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CategoriesService categoriesService = new CategoriesService();

        categoryModelHashMap = categoriesService.get();
        setupTableColumns();
        initializeEventListeners(categoryModelHashMap);

        Scene scene = new Scene(new Group(), 500, 400);
        scene.getStylesheets().add("stylesheets/posStyles.css");
    }

    private void initializeEventListeners(HashMap<String, CategoryModel> categoryModelHashMap) {

        initializeCategoryView(categoryModelHashMap);
        initializeReceiptTable();
        initializeFoodView();
    }

    private void initializeCategoryView(HashMap<String, CategoryModel> categoryModelHashMap) {

        categoryListView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent enterKeyPressed) {
                if (enterKeyPressed.getCode().equals(KeyCode.ENTER)) {
                    populateCategoriesList(categoryModelHashMap);
                }
            }
        });
    }

    private void populateCategoriesList(HashMap<String, CategoryModel> categoriesModelHashMap) {

        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (CategoryModel categoryModel : categoriesModelHashMap.values()) {

            observableList.add(categoryModel.getName());
        }
        categoryListView.setItems(observableList);
    }

    private void initializeFoodView() {

        categoryListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                String categoryName = categoryListView.getSelectionModel().getSelectedItem().toString();

                populateFoodItemsListView(categoryName);
            }
        });
    }

    private void populateFoodItemsListView(String categoryName) {

        ObservableList<String> observableList = FXCollections.observableArrayList();
        CategoryModel categoryModel = categoryModelHashMap.get(categoryName);
        ArrayList<FoodItemModel> foodItemsList = categoryModel.getFoodItemsList();

        for (FoodItemModel foodItemModel : foodItemsList) {

            observableList.add(foodItemModel.getName());
        }
        foodItemsListView.setItems(observableList);
    }

    private void initializeReceiptTable() {

        foodItemsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                String selectedFoodName = foodItemsListView.getSelectionModel().getSelectedItem().toString();
                String categoryName = categoryListView.getSelectionModel().getSelectedItem().toString();
                CategoryModel categoryModel = categoryModelHashMap.get(categoryName);

                ArrayList<FoodItemModel> foodItem = categoryModel.getFoodItemsList();
                for (FoodItemModel foodItemModel : foodItem) {
                    if (selectedFoodName.equals(foodItemModel.getName())) {

                        FoodItemModel foodItemsList = categoryModel.find(selectedFoodName);
                        selectedFoodItemsToDisplay.addAll(foodItemsList);

                        Double individualItemPrice = Double.valueOf(foodItemModel.getPrice());
                        receipt.updateTotal(individualItemPrice);
                    }
                }
                taxField.setText(String.valueOf(receipt.getTax()));
                totalField.setText(String.valueOf(receipt.getGrandTotal()));
                receiptTableView.setItems(selectedFoodItemsToDisplay);
            }
        });
    }


    private void setupTableColumns() {

        TableColumn<FoodItemModel, String> numberColumn = new TableColumn<>("#");
        numberColumn.setMaxWidth(50);
        numberColumn.setCellValueFactory(
                new PropertyValueFactory<>("#"));
        numberColumn.setId("numberColumn");

        TableColumn<FoodItemModel, String> descriptionColumn = new TableColumn<>("Name");
        descriptionColumn.setMinWidth(225);
        descriptionColumn.setCellValueFactory(
                new PropertyValueFactory<>("Name"));
        descriptionColumn.setId("descriptionColumn");

        TableColumn<FoodItemModel, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(110);
        priceColumn.setCellValueFactory(
                new PropertyValueFactory<>("Price"));
        priceColumn.setId("priceColumn");

        receiptTableView.getColumns().addAll(numberColumn, descriptionColumn, priceColumn);
    }
}
