package com.manager;

public class RetailStrategy implements SalesStrategy{
    @Override
    public double setTaxRate(Double basePrice){
        //9.05% sales tax boulder, co
        double tax = .0905;
        double price;

        price = basePrice + (tax * basePrice);
        //round 2 decimal places
        //Source: https://www.geeksforgeeks.org/java-program-to-round-a-number-to-n-decimal-places/
        price = Math.round(price * Math.pow(10,2))/Math.pow(10,2);

        return price;
    }
}