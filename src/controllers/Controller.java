package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import models.CategoryModel;
import services.CategoriesService;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    ListView categoryListView;

    @FXML
    Pane backPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        populateListView();

    }



    private void populateListView() {
        CategoriesService categoriesService = new CategoriesService();
        HashMap<String, CategoryModel> mappedCategories = categoriesService.get();

        categoryListView.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent enterKeyPressed) {

                if (enterKeyPressed.getCode().equals(KeyCode.ENTER)) {

                    initializeListView(mappedCategories);

                }

            }
        });
    }


    private void initializeListView(HashMap<String, CategoryModel> categoriesHashMap) {

        ObservableList<String> observableList = FXCollections.observableArrayList();

        for (CategoryModel categoryModel  : categoriesHashMap.values()) {
            String name = categoryModel.getName();

            observableList.add(name);
        }

        categoryListView.setItems(observableList);

    }


}

