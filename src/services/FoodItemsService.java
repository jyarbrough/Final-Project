package services;

import mappers.FoodItemMapper;
import models.FoodItemModel;
import java.util.ArrayList;

public class FoodItemsService {

    public ArrayList<FoodItemModel> get(String categoryTitle) {

        CsvReader csvReader = new CsvReader();
        final FoodItemMapper foodItemMapper = new FoodItemMapper();
        String foodItemsPath = "/Users/joeyarbrough/Advanced-Java-Labs/Homework/Final-Project/src/csvFiles/" + categoryTitle + ".csv";

        ArrayList<String> fetchedData = csvReader.fetch(foodItemsPath);

        ArrayList<FoodItemModel> mappedFoodItems = foodItemMapper.map(fetchedData);

        return mappedFoodItems;
    }
}
