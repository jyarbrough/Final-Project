package models;

import services.EmployeeService;

import java.util.HashMap;

public class EmployeeModel extends EmployeeService {

    private String logInCode;
    private String name;
    private String id;

    public EmployeeModel(String logInCode, String name, String id) {

        this.logInCode = logInCode;
        this.name = name;
        this.id = id;
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
