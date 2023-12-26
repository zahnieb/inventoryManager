package com.command;

import com.manager.Inventory;
import com.manager.Item;
import java.util.Scanner;

public class Edit implements Command{

    public Edit(){};

    @Override
    public void execute(String type) {

        Inventory inventory = new Inventory();

        if(type.equals("item")) {
            System.out.println();
            inventory.getItemsList();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Item SKU: ");
            int SKU = scanner.nextInt();
            System.out.println("Enter New Sales Price: ");
            double sales_price = scanner.nextDouble();
            inventory.updateSalesPrice(SKU, sales_price);
        }
        else if(type.equals("sales")){
            System.out.println();
            inventory.getSalesInvoiceList();
            System.out.println();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Sales Invoice Number to view finalized sales for customer: ");
            int invoiceNumber = scanner.nextInt();
            inventory.finalizeSale(invoiceNumber);
            inventory.getCustomers();
        }


    }


}
