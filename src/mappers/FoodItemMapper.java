package mappers;

import models.FoodItemModel;

import java.util.ArrayList;
import java.util.HashMap;

public class FoodItemMapper extends ArrayList<FoodItemModel> {

    public ArrayList<FoodItemModel> map(ArrayList<String> linesFromFile) {

        ArrayList<FoodItemModel> foodItemsHashMap = new ArrayList<>();

        for (String line : linesFromFile) {
            String[] foodItemsArray = line.split(",");
            String id = foodItemsArray[0];
            String name = foodItemsArray[1];
            String description = foodItemsArray[2];
            String price = foodItemsArray[3];

            FoodItemModel foodItemModel = new FoodItemModel(id, name, description, price);
            foodItemsHashMap.add(foodItemModel);
        }
        return foodItemsHashMap;
    }
}
