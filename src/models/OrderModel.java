package models;

import enums.OperationMode;

import java.util.ArrayList;

public class OrderModel {

    private double subTotal = 0;
    private double tax = 0;
    private Double grandTotal;
    private double currentTaxRate = 0.08;
    private double coupon = 0;
    private OperationMode operationMode;
    private Integer ticketNumber = null;
    private Boolean isOpen = true;
    private ArrayList<FoodItemModel> foodItems;
    private EmployeeModel employee;
    private CustomerModel customer;

    public OperationMode getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(OperationMode operationMode) {
        this.operationMode = operationMode;
    }

    public EmployeeModel getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeModel employee) {
        this.employee = employee;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public Integer getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(Integer ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public ArrayList<FoodItemModel> getFoodItems() { return foodItems; }

    public void setFoodItems(ArrayList<FoodItemModel> foodItems) { this.foodItems = foodItems; }

    public double getTax() {
        return tax;
    }

    private void setTax(double tax) {
        this.tax = tax;
    }

    public Double getGrandTotal() {
        return grandTotal;
    }

    private void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Boolean isOpen() {
        return isOpen;
    }

    public void setOpen(Boolean open) {
        isOpen = open;
    }

    public void addItems(Double itemPrice) {

        subTotal += itemPrice;
        tax = subTotal * currentTaxRate;
        grandTotal = subTotal + tax;
        setTax(tax);
        setGrandTotal(grandTotal);
    }

    public void removeItem(Double itemToDelete) {

        subTotal -= itemToDelete;
        tax = subTotal * currentTaxRate;
        grandTotal = subTotal + tax;
        setTax(tax);
        setGrandTotal(grandTotal);
    }
}
