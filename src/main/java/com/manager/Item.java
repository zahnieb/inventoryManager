package com.manager;

public class Item {
    private int SKU;
    private String name;
    private String description;
    private double purchasePrice;
    private double salesPrice;
    SalesStrategy salesStrategy;

    //TODO: Constructor for Items with parameters

    public void setSKU(int input) {
        this.SKU = input;
    }

    public void setName(String input) {
        this.name = input;
    }

    public void setDescription(String input) {
        this.description = input;
    }

    public void setPurchasePrice(double input) {
        this.purchasePrice = input;
    }

    //uses salesStrategy to set sales price (wholesale or retail)
    public void setSalesPrice(double input) {

        //if null we get salesPrice from Database connection
        if (this.salesStrategy == null){
            this.salesPrice = input;
            return;
        }

        //used when we create a new item not already in Database
        this.salesPrice = this.salesStrategy.setTaxRate(input);
    }

    public void setSalesStrategy(SalesStrategy strategy) {
        this.salesStrategy = strategy;
    }

    public String getName() {
        return this.name;
    }

    public int getSKU() {
        return this.SKU;
    }

    public double getPurchasePrice() {
        return this.purchasePrice;
    }

    public double getSalesPrice() {
        return this.salesPrice;
    }

    public String getDescription() {
        return this.description;
    }
}