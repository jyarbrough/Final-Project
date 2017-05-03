package models;

import java.util.ArrayList;

public class ReceiptModel {

    private double subTotal = 0;
    private double tax = 0;
    private double grandTotal;
    private double currentTaxRate = 0.08;
    private double coupon = 0;

    private Integer ticketNumber = 0;
    private ArrayList<FoodItemModel> foodItems;
    private EmployeeModel employee;
    private CustomerModel customer;


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

    public ArrayList<FoodItemModel> getFoodItems() {

        return foodItems;
    }

    public void setFoodItems(ArrayList<FoodItemModel> foodItems) {

        this.foodItems = foodItems;
    }

    public void updateTotal(Double individualItemPrice) {

        subTotal += individualItemPrice;
        tax = subTotal * currentTaxRate;
        grandTotal = subTotal + tax;
        setTax(tax);
        setGrandTotal(grandTotal);
    }

    public double getTax() {
        return tax;
    }

    private void setTax(double tax) {
        this.tax = tax;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    private void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }
}
