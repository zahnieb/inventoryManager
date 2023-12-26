package com.command;

import com.manager.Inventory;
import com.manager.Item;
import java.util.Scanner;

public class Remove implements Command{

    public Remove(){};

    @Override
    public void execute(String type) {
        //Item item = new Item();

        Inventory inventory = new Inventory();

        if(type.equals("item")){
            inventory.getItemsList();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter SKU to Remove: ");
            int SKU = scanner.nextInt();
            inventory.removeItemFromInventory(SKU);
        }
        else if(type.equals("customer")){
            inventory.getCustomers();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Customer ID to remove: ");
            int ID = scanner.nextInt();
            inventory.removeCustomerByID(ID);
        }
        else if(type.equals("sales")){
            inventory.getSalesInvoiceList();
            System.out.println();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Invoice Number to remove: ");
            int invoiceNumber = scanner.nextInt();
            inventory.deleteInvoice(invoiceNumber);
        }


    }


}
