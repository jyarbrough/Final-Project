package services;

import factories.CategoryFactory;
import models.CategoryModel;

import java.util.ArrayList;
import java.util.HashMap;

public class CategoriesService {

    private final CategoryFactory categoryFactory = new CategoryFactory();

    public HashMap<String, CategoryModel> get() {

        CsvReader csvReader = new CsvReader();
        String categoryPath = "/Users/joeyarbrough/Advanced-Java-Labs/Homework/Final-Project/src/csvFiles/Categories.csv";
        ArrayList<String> fetchedData = csvReader.fetch(categoryPath);

        return categoryFactory.build(fetchedData);
    }
}
