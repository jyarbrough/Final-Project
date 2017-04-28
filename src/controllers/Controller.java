package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
    public TableView receiptTable;

    public Pane backPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CategoriesService categoriesService = new CategoriesService();
        FoodItemsService foodItemsService = new FoodItemsService();
         categoryModelHashMap = categoriesService.get();

        initializeEventListeners(categoryModelHashMap);
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
        for (CategoryModel categoryModel  : categoriesModelHashMap.values()) {
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

}

