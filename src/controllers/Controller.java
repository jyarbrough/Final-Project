package controllers;

import factories.CategoryFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import models.CategoryModel;
import models.FoodItemModel;
import models.ReceiptModel;
import services.CategoriesService;
import services.FoodItemsService;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public Pane foodItemsPane;
    public TilePane foodItemPane;
    public ListView categoryListView;
    public ListView foodItemsListView;
    public Pane backPane;
    public TextField totalField;
    public TextField taxField;
    public TableView<FoodItemModel> receiptTableView;
    public TilePane categoryPane;

    private ReceiptModel receipt = new ReceiptModel();
    private ObservableList<FoodItemModel> selectedFoodItemsToDisplay = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CategoriesService categoriesService = new CategoriesService();

        HashMap<String, CategoryModel> categoryModelHashMap = categoriesService.get();
        setupReceiptColumns();
        initializeEventListeners(categoryModelHashMap);

        Scene scene = new Scene(new Group(), 500, 400);
        scene.getStylesheets().add("stylesheets/posStyles.css");
    }

    private void initializeEventListeners(HashMap<String, CategoryModel> categoryModelHashMap) {
        initializeCategoryPane();
//        initializeReceiptTable();
        initializeItemsInterface(categoryModelHashMap);
    }


    private void initializeCategoryPane() {
        CategoriesService categoriesService = new CategoriesService();
        HashMap<String, CategoryModel> categoryModelHashMap = categoriesService.get();


        categoryListView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent enterKeyPressed) {
                if (enterKeyPressed.getCode().equals(KeyCode.ENTER)) {
                    populateCategoriesList(categoryModelHashMap);
                }
            }
        });
    }

    private void initializeItemsInterface(HashMap<String, CategoryModel> categoryModelHashMap) {

        categoryListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                foodItemPane.getChildren().clear();
                populateItemsInterface(categoryModelHashMap);
            }
        });

//        categoryListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                foodItemPane.getChildren().clear();
//                populateItemsInterface();
//            }
//        });
    }

    private void populateCategoriesList(HashMap<String, CategoryModel> categoriesModelHashMap) {

        ObservableList<String> observableList = FXCollections.observableArrayList();
        for (CategoryModel categoryModel : categoriesModelHashMap.values()) {

            observableList.add(categoryModel.getName());
        }

        categoryListView.setItems(observableList);
    }

    private void populateItemsInterface(HashMap<String, CategoryModel> categoryModelHashMap) {

        FoodItemsService foodItemsService = new FoodItemsService();

        String categoryName = categoryListView.getSelectionModel().getSelectedItem().toString();
        ArrayList<FoodItemModel> foodItemModelArrayList = foodItemsService.get(categoryName);

        for (FoodItemModel foodItemModel : foodItemModelArrayList) {
            final Button tempButton = new Button(foodItemModel.getName());
            tempButton.setMinHeight(69);
            tempButton.setMinWidth(159);

            foodItemPane.getChildren().addAll(tempButton);
            tempButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    String selectedItem = tempButton.getText();

                    addItemToReceipt(selectedItem, categoryName, categoryModelHashMap);
                }
            });
        }


    }

    private void addItemToReceipt(String selectedItem, String categoryName, HashMap<String, CategoryModel> categoryModelHashMap) {

        FoodItemsService foodItemsService = new FoodItemsService();
        ArrayList<FoodItemModel> foodItemModelArrayList = foodItemsService.get(categoryName);
        CategoryModel categoryModel = categoryModelHashMap.get(categoryName);

        for (FoodItemModel foodItemModel : foodItemModelArrayList) {
            if (selectedItem.equals(foodItemModel.getName())) {

                FoodItemModel foodItemsList = categoryModel.find(selectedItem);
                selectedFoodItemsToDisplay.addAll(foodItemsList);

                Double individualItemPrice = Double.valueOf(foodItemModel.getPrice());
                receipt.updateTotal(individualItemPrice);

            }
        }

        taxField.setText(String.valueOf(receipt.getTax()));
        totalField.setText(String.valueOf(receipt.getGrandTotal()));
        receiptTableView.setItems(selectedFoodItemsToDisplay);
    }

    private void initializeReceiptTable() {

//        foodItemsListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//
//                String selectedFoodName = foodItemsListView.getSelectionModel().getSelectedItem().toString();
//                String categoryName = categoryListView.getSelectionModel().getSelectedItem().toString();
//                CategoryModel categoryModel = categoryModelHashMap.get(categoryName);
//
//                ArrayList<FoodItemModel> foodItem = categoryModel.getFoodItemsList();
//                for (FoodItemModel foodItemModel : foodItem) {
//                    if (selectedFoodName.equals(foodItemModel.getName())) {
//
//                        FoodItemModel foodItemsList = categoryModel.find(selectedFoodName);
//                        selectedFoodItemsToDisplay.addAll(foodItemsList);
//
//                        Double individualItemPrice = Double.valueOf(foodItemModel.getPrice());
//                        receipt.updateTotal(individualItemPrice);
//                    }
//                }
//                taxField.setText(String.valueOf(receipt.getTax()));
//                totalField.setText(String.valueOf(receipt.getGrandTotal()));
//                receiptTableView.setItems(selectedFoodItemsToDisplay);
//            }
//        });
    }


    private void setupReceiptColumns() {

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
