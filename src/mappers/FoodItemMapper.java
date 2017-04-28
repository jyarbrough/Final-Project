package mappers;

import models.FoodItemModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FoodItemMapper extends ArrayList<FoodItemModel> {

    public HashMap<String, FoodItemModel> map(ArrayList<String> linesFromFile) {

        HashMap<String, FoodItemModel> foodItemsHashMap = new HashMap<>();

        for (String line : linesFromFile) {
            String[] foodItemsArray = line.split(",");
            String id = foodItemsArray[0];
            String name = foodItemsArray[1];
            String description = foodItemsArray[2];
            String price = foodItemsArray[3];

            FoodItemModel foodItemModel = new FoodItemModel(id, name, description, price);
            foodItemsHashMap.put(foodItemModel.getId(), foodItemModel);
        }
        return foodItemsHashMap;
    }
}
