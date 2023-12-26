package com.command;


import com.manager.Inventory;
public class GetTotalSales implements Command{

    public GetTotalSales(){}
    @Override
    public void execute(String type) {
        Inventory inventory = new Inventory();
        System.out.println();
        Double total = inventory.getTotalValue();
        System.out.println("======== TOTAL INVENTORY VALUE ========\n" +
                 String.format("               $%.2f", total) +
                "\n=======================================");

    }
}