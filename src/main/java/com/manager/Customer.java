package com.manager;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Customer {
    String customerName;
    String phone;
    double totalPurchases;
    private int customerID;

    public Customer() {
        //new customers have no prev purchases
        this.totalPurchases = 0.0;
    }

    public void setCustomerName(String name) {
        this.customerName = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public String getPhone() {
        return this.phone;
    }

    public double getTotalPurchases() {
        return this.totalPurchases;
    }

    public void setTotalPurchase(double total) {
        this.totalPurchases = total;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getCustomerID() {
        return this.customerID;
    }


}
