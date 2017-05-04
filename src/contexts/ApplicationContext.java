package contexts;

import enums.OperationMode;
import models.CustomerModel;
import models.EmployeeModel;

public class ApplicationContext {

    private CustomerModel currentCustomer;
    private EmployeeModel loggedInEmployee;
    private OperationMode operationMode;

    private final static ApplicationContext instance = new ApplicationContext();

    public static ApplicationContext getInstance() { return instance; }

    public OperationMode getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(OperationMode operationMode) {
        this.operationMode = operationMode;
    }

    public EmployeeModel getLoggedInEmployee() {
        return loggedInEmployee;
    }

    public void setLoggedInEmployee(EmployeeModel loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
    }

    public CustomerModel getCurrentCustomer() { return currentCustomer; }

    public void setCurrentCustomer(CustomerModel currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

}
