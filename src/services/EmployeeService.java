package services;

import mappers.EmployeeMapper;
import models.EmployeeModel;

import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeService {

    public HashMap<String, EmployeeModel> get() {

        CsvReader csvReader = new CsvReader();
        final EmployeeMapper employeeMapper = new EmployeeMapper();
        String employeeListPath = "/Users/joeyarbrough/Advanced-Java-Labs/Homework/Final-Project/src/csvFiles/employees.csv";

        ArrayList<String> fetchedData = csvReader.fetch(employeeListPath);

        return employeeMapper.map(fetchedData);

    }
}
