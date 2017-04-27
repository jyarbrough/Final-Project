package mappers;

import models.CategoryModel;
import services.Categories;
import java.util.ArrayList;
import java.util.HashMap;

public class CategoryMapper {

    private HashMap<String, Categories> map(ArrayList<String> linesFromFile) {

        HashMap<String, Categories> categoriesHashMap = new HashMap<>();

        for (String line : linesFromFile) {
            String[] categoriesArray = line.split(",");
            String id = categoriesArray[0];
            String name = categoriesArray[1];

            CategoryModel category = new CategoryModel(id, name);
            categoriesHashMap.put(category.getId(), category);

        }
        return categoriesHashMap;
    }
}
