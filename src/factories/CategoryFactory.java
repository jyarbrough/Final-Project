package factories;

import mappers.CategoryMapper;
import models.CategoryModel;
import models.FoodItemModel;
import services.CsvReader;
import services.FoodItemsService;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoryFactory {

    private CategoryMapper categoryMapper;
    private FoodItemsService foodItemsService;

    public CategoryFactory() {
        this.categoryMapper = new CategoryMapper();
        this.foodItemsService = new FoodItemsService();
    }

    public HashMap<String, CategoryModel> build(ArrayList<String> fetchedData) {

        HashMap<String, CategoryModel> mappedCategories = categoryMapper.map(fetchedData);

        for (CategoryModel categoryModel : mappedCategories.values()) {
            String categoryTitle = categoryModel.getId();
            ArrayList<FoodItemModel> foodItems = foodItemsService.get(categoryTitle);
            categoryModel.setFoodItemsList(foodItems);
        }

        return mappedCategories;
    }
}