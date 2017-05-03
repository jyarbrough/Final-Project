package contexts;

import models.CustomerModel;
import models.EmployeeModel;

public class ApplicationContext {

    private CustomerModel currentCustomer;
    private EmployeeModel loggedInEmployee;

    private final static ApplicationContext instance = new ApplicationContext();

    public static ApplicationContext getInstance() { return instance; }

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
