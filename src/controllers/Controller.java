package controllers;

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
    public TilePane foodItemPane;
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

        initializeCategoryPane(categoryModelHashMap);

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
