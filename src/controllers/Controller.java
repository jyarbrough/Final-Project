package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
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
//    public GridPane gridPane;
    public Pane foodItemsPane;
    public Pane blankPane;
    public TilePane tilePane;
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
                initializeGridButtons(categoryName);
            }
        });
    }

    private void initializeGridButtons(String name) {

        FoodItemsService foodItemsService = new FoodItemsService();
        String categoryName = categoryListView.getSelectionModel().getSelectedItem().toString();
        ArrayList<FoodItemModel> foodItemModelArrayList = foodItemsService.get(categoryName);

        ArrayList<FoodItemModel> tempArray = new ArrayList<>();

        for (FoodItemModel foodItemModel : foodItemModelArrayList) {
            final Button tempButton = new Button(foodItemModel.getName());
            tempButton.setMinHeight(69);
            tempButton.setMinWidth(159);


            tempButton.setId("interfaceButton");

            tilePane.getChildren().addAll(tempButton);

//            for (int i = 0; i < foodItemModelArrayList.size(); i++) {
//
//                String buttonTitle = foodItemModel.getName();
//
//                if(i <= 5) {
//
//                }
//
//                if (i > 5 && i <= 11) {
//
//                }
//
//                if (i > 12 && i <= 18) {
//
//                }
//
//
//            }
        }





//        while(i <= 5) {
//
//
//
//            for (int j = 0; j <= 5; j++) {
//                addButtonColumn0(i, name);
//            }
//
//            for (int k = 6; k <= 11; k++) {
//
//                addButtonColumn1(i, name);
//
//            }
//
//            for (int l = 12; l <= 18; l++) {
//                addButtonColumn2(i, name);
//            }
//
//
//
//        }
//
//        gridPane.add(temp, 0, i);
    }

//    private void addButtonColumn1(int i, String name) {
//
//    }
//
//    private void addButtonColumn2(int i, String name) {
//
//    }
//
//    private void populateGridPane() {
//
//    }

//    private void populateFoodItemsListView(String categoryName) {

//        ObservableList<String> observableList = FXCollections.observableArrayList();
//        CategoryModel categoryModel = categoryModelHashMap.get(categoryName);
//        ArrayList<FoodItemModel> foodItemsList = categoryModel.getFoodItemsList();
//
//        for (FoodItemModel foodItemModel : foodItemsList) {
//
//            observableList.add(foodItemModel.getName());
//        }
//        foodItemsListView.setItems(observableList);
//    }

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
