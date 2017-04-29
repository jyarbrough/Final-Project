package controllers;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import models.CategoryModel;
import models.FoodItemModel;
import services.CategoriesService;
import services.FoodItemsService;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    private HashMap<String, CategoryModel> categoryModelHashMap;

    @FXML
    public ListView categoryListView;
    public ListView foodItemsListView;
    public Pane backPane;
    private ObservableList<FoodItemModel> foodItem = FXCollections.observableArrayList();
    public TableView<FoodItemModel> receiptTableView = new TableView<>(foodItem);

//    public TableColumn<FoodItemModel, String> numberColumn;
//    public TableColumn<FoodItemModel, String> nameColumn;
//    public TableColumn<FoodItemModel, String> priceColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CategoriesService categoriesService = new CategoriesService();
        FoodItemsService foodItemsService = new FoodItemsService();
        categoryModelHashMap = categoriesService.get();
        setupTableColumns();
        initializeReceiptTable();
        initializeEventListeners(categoryModelHashMap);

        Scene scene = new Scene(new Group(), 500, 400);
        scene.getStylesheets().add("./posStyles.css");
    }

    private void initializeEventListeners(HashMap<String, CategoryModel> categoryModelHashMap) {

        initializeCategoryView(categoryModelHashMap);
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
            String name = categoryModel.getName();

            observableList.add(name);
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
            String name = foodItemModel.getName();

            observableList.add(name);
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

                populateReceiptView(selectedFoodName, categoryModel);
            }
        });
    }

    private void populateReceiptView(String selectedFoodName, CategoryModel categoryModel) {
        ArrayList<FoodItemModel> foodItem = categoryModel.getFoodItemsList();
        for (FoodItemModel foodItemModel : foodItem) {
            if (selectedFoodName.equals(foodItemModel.getName())) {

                try {
                    FoodItemModel foodItemsList = categoryModel.find(selectedFoodName);
                    ObservableList<FoodItemModel> observableFoodItem = FXCollections.observableArrayList();
                    observableFoodItem.addAll(foodItemsList);
                    receiptTableView.setItems(observableFoodItem);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
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
