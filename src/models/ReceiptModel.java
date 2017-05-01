package models;

public class ReceiptModel {

    private double subTotal = 0;
    private double tax = 0;
    private double grandTotal;
    private double currentTaxRate = 0.08;
    private double coupon = 0;
    private String employeeId;
    private String customerId;
    private String itemsPurchased = null;

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
