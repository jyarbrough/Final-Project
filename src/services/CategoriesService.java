package services;

import mappers.CategoryMapper;
import models.CategoryModel;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoriesService {

    public HashMap<String, CategoryModel> get() {
        CsvReader csvReader = new CsvReader();
        CategoryMapper categoryMapper = new CategoryMapper();

        String categoryPath = "/Users/joeyarbrough/Advanced-Java-Labs/Homework/Final-Project/src/csvFiles/Categories.csv";

        ArrayList<String> fetchedData = csvReader.fetch(categoryPath);
        HashMap<String, CategoryModel> mappedCategories = categoryMapper.map(fetchedData);

        return mappedCategories;
    }
}
