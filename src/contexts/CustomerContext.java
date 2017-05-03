package contexts;

import models.CustomerModel;

public class CustomerContext {

    private CustomerModel customerModel;

    private final static CustomerContext instance = new CustomerContext();

    public static CustomerContext getInstance() { return instance; }

    public CustomerModel getCustomerModel() { return customerModel; }

    public void setCustomerModel(CustomerModel customerModel) {
        this.customerModel = customerModel;
    }
}
