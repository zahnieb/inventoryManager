package com.command;

import com.manager.Inventory;

public class NewSalesInvoice implements Command{

    public NewSalesInvoice(){}

    @Override
    public void execute(String type) {

        Inventory inventory = new Inventory();

        inventory.newSalesInvoice();
    }

}
