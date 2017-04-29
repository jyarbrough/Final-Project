package mappers;

import models.CategoryModel;
import java.util.ArrayList;
import java.util.HashMap;

public class CategoryMapper {

    public HashMap<String, CategoryModel> map(ArrayList<String> linesFromFile) {

        HashMap<String, CategoryModel> categoriesHashMap = new HashMap<>();

        for (String line : linesFromFile) {
            String[] categoriesArray = line.split(",");
            String id = categoriesArray[0];
            String name = categoriesArray[1];

            CategoryModel category = new CategoryModel(id, name);
            categoriesHashMap.put(category.getName(), category);
        }
        return categoriesHashMap;
    }
}
