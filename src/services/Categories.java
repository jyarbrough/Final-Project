package services;

import mappers.CategoryMapper;

import java.util.ArrayList;
import java.util.HashMap;

public class Categories {

    private HashMap<String, Categories> get() {

        CsvReader csvReader = new CsvReader();

        ArrayList<String> fetchedData = csvReader.fetch();

        CategoryMapper categoryMapper = new CategoryMapper();

        HashMap<String, Categories> mappedCategories = categoryMapper.map(fetchedData);

        return mappedCategories;
    }

}
