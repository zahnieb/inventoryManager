package com.command;

import com.manager.*;

import java.util.Scanner;

public class Add implements Command{

    public Add(){}
    @Override
    public void execute(String type) {
        Item item = new Item();
        Customer customer = new Customer();

        Inventory inventory = new Inventory();

        if(type.equals("item")) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Item Name: ");
            String name = scanner.nextLine();
            item.setName(name);
            System.out.print("Enter Description of Item: ");
            String description = scanner.nextLine();
            item.setDescription(description);
            System.out.print("Enter the Purchase Price of Item: ");
            double purchasePrice = scanner.nextDouble();
            item.setPurchasePrice(purchasePrice);
            System.out.print("Enter Sales Strategy: \n");
            System.out.print("(1) Retail Tax Strategy: \n");
            System.out.print("(2) Wholesale Tax Strategy: \n");
            if (scanner.nextInt() == 1){
                item.setSalesStrategy(new RetailStrategy());
            } else {
                item.setSalesStrategy(new WholesaleStrategy());
            }
            System.out.print("Enter the Sales Price of Item: ");
            double salesPrice = scanner.nextDouble();
            item.setSalesPrice(salesPrice);
            inventory.addItem(item);
        }
        else if(type.equals("customer")){
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Customer's Name: ");
            String name = scanner.nextLine();
            customer.setCustomerName(name);
            System.out.print("Enter Customer's Phone Number: ");
            String phoneNumber = scanner.nextLine();
            customer.setPhone(phoneNumber);
            System.out.print("Enter Customer's Total Purchases: ");
            double totalPurchase = scanner.nextDouble();
            customer.setTotalPurchase(totalPurchase);
            inventory.addCustomer(customer);
        }
        else if(type.equals("sales")){
            inventory.getSalesInvoiceList();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Sales Invoice Number: ");
            int invoiceNumber = scanner.nextInt();
            inventory.getItemsList();
            System.out.print("Enter SKU: ");
            int SKU = scanner.nextInt();
            System.out.print("Enter Quantity: ");
            int quantity = scanner.nextInt();
            inventory.getCustomers();
            System.out.print("Enter Customer ID: ");
            int customer_id = scanner.nextInt();
            inventory.insertToSalesInvoice(invoiceNumber, SKU, quantity, customer_id);
        }


    }
}