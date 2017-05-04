package contexts;

import enums.OperationMode;
import models.CustomerModel;
import models.EmployeeModel;
import models.ReceiptModel;

import java.util.HashMap;

public class ApplicationContext {

    public ApplicationContext() {
        this.receipts = new HashMap<>();
    }

    private CustomerModel currentCustomer;
    private EmployeeModel loggedInEmployee;
    private OperationMode operationMode;
    private HashMap<Integer, ReceiptModel> receipts;

    private final static ApplicationContext instance = new ApplicationContext();

    public static ApplicationContext getInstance() { return instance; }

    public HashMap<Integer, ReceiptModel> getReceipts() {
        return receipts;
    }

    public void saveReceipt(ReceiptModel receipt) {
        Integer ticketNumber = receipt.getTicketNumber();
        receipts.put(ticketNumber, receipt);
    }

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
