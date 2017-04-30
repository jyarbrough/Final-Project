package models;

import services.EmployeeService;

import java.util.HashMap;

public class EmployeeModel extends EmployeeService {

    private String logInCode;
    private String name;
    private String id;
    HashMap<String, EmployeeModel> employeeList = null;

    public HashMap<String, EmployeeModel> getEmployeeList() { return employeeList; }

    public void setEmployeeList(HashMap<String, EmployeeModel> employeeList) {
        this.employeeList = employeeList;
    }

    public EmployeeModel(String logInCode, String name, String id) {

        this.logInCode = logInCode;
        this.name = name;
        this.id = id;
    }

    public HashMap<String, EmployeeModel> find(String searchCode) {
        HashMap<String, EmployeeModel> foundEmployee = null;

        for (String logIn : employeeList.keySet()) {
            String lookUpCode = String.valueOf(employeeList.get(logIn));

            if (lookUpCode.equals(logIn)) {
                foundEmployee = getEmployeeList();
            }
        }
        return foundEmployee;
    }

    public String getLogInCode() {
        return logInCode;
    }

    public void setLogInCode(String logInCode) {
        this.logInCode = logInCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
