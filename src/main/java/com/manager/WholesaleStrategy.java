package com.manager;

public class WholesaleStrategy implements SalesStrategy{
    @Override
    public double setTaxRate(Double basePrice) {
       return basePrice;
    }
}



