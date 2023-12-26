package com.manager;

public abstract class Invoice {
    private int salesInvoiceNumber;
    private double total;
    private int customerID;

    public Invoice(){};

    public void setSalesInvoiceNumber(int invoiceNumber) {
        this.salesInvoiceNumber = invoiceNumber;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getSalesInvoiceNumber() {
        return this.salesInvoiceNumber;
    }

    public double getTotal(){
        return this.total;
    }

    public int getCustomerID(){
        return this.customerID;
    }
}
