package mappers;

import models.EmployeeModel;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeMapper extends ArrayList<EmployeeModel> {

    public HashMap<String, EmployeeModel> map(ArrayList<String> linesFromFile) {

        HashMap<String, EmployeeModel> employeeHashMap = new HashMap<>();

        for (String line : linesFromFile) {
            String[] employeeListArray = line.split(",");
            String logInCode = employeeListArray[0];
            String name = employeeListArray[1];
            String id = employeeListArray[2];

            EmployeeModel employeeModel = new EmployeeModel(logInCode, name, id);
            employeeHashMap.put(logInCode, employeeModel);
        }
        return employeeHashMap;
    }
}
