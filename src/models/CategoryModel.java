package models;

import services.CategoriesService;

import java.util.ArrayList;

public class CategoryModel extends CategoriesService {

    private String name;
    private String id;
    private ArrayList<FoodItemModel> foodItemsList = null;

    public ArrayList<FoodItemModel> getFoodItemsList() {
        return foodItemsList;
    }

    public void setFoodItemsList(ArrayList<FoodItemModel> foodItemsList) {
        this.foodItemsList = foodItemsList;
    }

    public FoodItemModel find(String searchName) {

        FoodItemModel foundFoodItem = null;

        for (FoodItemModel foodItemModel : foodItemsList) {
            String name = foodItemModel.getName();

            if (name.equals(searchName)) {
                foundFoodItem = foodItemModel;
            }
        }
        return foundFoodItem;
    }

    public CategoryModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
