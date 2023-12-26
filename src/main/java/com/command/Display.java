package com.command;
import com.manager.Inventory;
import com.manager.Item;
import java.util.Scanner;

public class Display implements Command{

    public Display(){}
    @Override
    public void execute(String type) {
        Inventory inventory = new Inventory();

        if(type.equals("item")) {
            inventory.getItemsList();
        }
        else if(type.equals("customer")){
            inventory.getCustomers();
        }
        else if(type.equals("sales")){
            inventory.getSalesInvoiceList();
        }
        else if(type.equals("sales_item")){
            System.out.println();
            inventory.getSalesInvoiceList();
            Scanner scanner = new Scanner(System.in);
            System.out.println();
            System.out.print("Enter Sales Invoice Number to view Sales Item Invoice: ");
            int invoiceNumber = scanner.nextInt();
            inventory.getSalesInvoiceItems(invoiceNumber);
        }




    }
}