package factories;

import mappers.EmployeeMapper;
import models.EmployeeModel;
import services.EmployeeService;
import java.util.ArrayList;
import java.util.HashMap;

public class EmployeeFactory {

    private EmployeeMapper employeeMapper;
    private EmployeeService employeeService;

    public EmployeeFactory() {
        this.employeeMapper = new EmployeeMapper();
        this.employeeService = new EmployeeService();
    }

    public HashMap<String, EmployeeModel> build(ArrayList<String> fetchedData) {

        HashMap<String, EmployeeModel> mappedEmployees = employeeMapper.map(fetchedData);


        for (EmployeeModel employeeModel : mappedEmployees.values()) {

            String employee = employeeModel.getLogInCode();
            HashMap<String, EmployeeModel> employeeLogIn = employeeService.get(employee);
            employeeModel.setEmployeeList(employeeLogIn);
        }
        return mappedEmployees;

    }
}
