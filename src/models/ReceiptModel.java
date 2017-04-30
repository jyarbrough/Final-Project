package models;

public class ReceiptModel {

    double sumOfPrices = 0;
    double tax = 0;
    double grandTotal;

    public void updateTotal(Double individualItemPrice) {
        sumOfPrices += individualItemPrice;

        tax = sumOfPrices * 0.08;

        grandTotal = sumOfPrices + tax;

        setTax(tax);
        setGrandTotal(grandTotal);
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }
}
