package com.manager;

import com.database.ConnectDatabase;
import com.engines.InventoryEngine;
import com.engines.CustomerEngine;
import com.engines.SalesInvoiceEngine;
import com.command.Invoker;
import com.Observer.Subject;
import com.Observer.Logger;
import com.Observer.Enums;

import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;

import java.util.List;

public class Inventory {
    private InventoryEngine inventoryEngine;
    private CustomerEngine customerEngine;
    private SalesInvoiceEngine salesInvoiceEngine;
    public Invoker invoker;
    public static Subject subject;

    public Inventory() {
        this.inventoryEngine = new InventoryEngine();
        this.customerEngine = new CustomerEngine();
        this.salesInvoiceEngine = new SalesInvoiceEngine();
        subject = new Subject();
        Logger logger = Logger.getLogger();
        if (!subject.isObserver(logger)) {
            subject.addObserver(logger);
        }
    }

    public void startMenu() {

        System.out.println("##########################################################");
        System.out.println("              Welcome to Inventory Manager");
        System.out.println("##########################################################");

        System.out.println();

        System.out.println("Press 'Enter' to go to main menu: ");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        mainMenu();
    }

    public void mainMenu(){
        subject.notifyObservers(Enums.EventType.START_MENU, "Opened Main Menu");
        System.out.println();
        System.out.println();

        Scanner scanner = new Scanner(System.in);

        System.out.println("##########################################################");
        System.out.println("                       Main Menu");
        System.out.println("##########################################################");
        System.out.println("----------------------------------------------------------");
        System.out.println("1. Items");
        System.out.println("----------------------------------------------------------");
        System.out.println("2. Customers");
        System.out.println("----------------------------------------------------------");
        System.out.println("3. Sales Invoice");
        System.out.println("----------------------------------------------------------");
        System.out.println("4. Exit");
        System.out.println("----------------------------------------------------------");
        System.out.println("##########################################################");
        System.out.println();
        System.out.print("Select which option you would like to access: ");

        try {
            int userInput = scanner.nextInt();

            switch (userInput) {
                case 1:
                    itemsInventory();
                    break;
                case 2:
                    customersList();
                    break;
                case 3:
                    salesinvoice();
                    break;
                case 4:
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Option. Please choose a valid option.");
                    break;
            }
        } catch (InputMismatchException e) { // Handle case if mismatch in input value
            System.out.println("Invalid input. Please enter a valid number (1-4).");
            scanner.nextLine(); // Read next input
        }
    }

    public void customersList(){
        subject.notifyObservers(Enums.EventType.CUSTOMER_LIST, "Opened Customer List");
        System.out.println();

        invoker = new Invoker();

        while (true) {
            boolean validCommandEntered = false;

            while (!validCommandEntered) {
                Scanner scanner = new Scanner(System.in);

                System.out.println("##########################################################");
                System.out.println("                       Customers");
                System.out.println("##########################################################");
                System.out.println("----------------------------------------------------------");
                System.out.println("1. Add Customer");
                System.out.println("----------------------------------------------------------");
                System.out.println("2. Remove Customer");
                System.out.println("----------------------------------------------------------");
                System.out.println("3. Display Customers");
                System.out.println("----------------------------------------------------------");
                System.out.println("4: Go back to Main Menu");
                System.out.println("----------------------------------------------------------");
                System.out.println("5: Exit");
                System.out.println("----------------------------------------------------------");
                System.out.println("##########################################################");
                System.out.println();
                System.out.print("Please enter an option 1-5: ");

                String command;
                String type = "customer";

                //Each input will be using command pattern
                try {
                    int userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1:
                            validCommandEntered = true;
                            command = "Add";
                            invoker.executeCommand(command, type);
                            break;
                        case 2:
                            validCommandEntered = true;
                            command = "Remove";
                            invoker.executeCommand(command, type);
                            break;
                        case 3:
                            validCommandEntered = true;
                            command = "Display";
                            invoker.executeCommand(command, type);
                            break;
                        case 4:
                            mainMenu();
                            break;
                        case 5:
                            validCommandEntered = true;
                            command = "Exit";
                            invoker.executeCommand(command,type);
                            break;
                        default:
                            System.out.println("Invalid command. Please choose a valid command.");
                            break;
                    }
                } catch (InputMismatchException e) { // Handle case if mismatch in input value
                    System.out.println("Invalid input. Please enter a valid number (1-5).");
                    scanner.nextLine(); // Read next input
                }
            }
        }
    }

    public void itemsInventory() {
        subject.notifyObservers(Enums.EventType.INVENTORY, "Opened Inventory");
        System.out.println();

        invoker = new Invoker();

        while (true) {
            boolean validCommandEntered = false;

            while (!validCommandEntered) {
                Scanner scanner = new Scanner(System.in);

                System.out.println();
                System.out.println("##########################################################");
                System.out.println("                     Items Inventory");
                System.out.println("##########################################################");
                System.out.println("----------------------------------------------------------");
                System.out.println("1: Add Item");
                System.out.println("----------------------------------------------------------");
                System.out.println("2: Remove Item");
                System.out.println("----------------------------------------------------------");
                System.out.println("3: Edit Sales Price of Item");
                System.out.println("----------------------------------------------------------");
                System.out.println("4: Display Items Inventory");
                System.out.println("----------------------------------------------------------");
                System.out.println("5: Get Total Sales Price Value");
                System.out.println("----------------------------------------------------------");
                System.out.println("6: Go back to Main Menu");
                System.out.println("----------------------------------------------------------");
                System.out.println("7: Exit");
                System.out.println("----------------------------------------------------------");
                System.out.println("##########################################################");
                System.out.print("Please enter an option 1-7: ");

                String command;
                String type = "item";

                //Each input will be using command pattern
                try {
                    int userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1:
                            validCommandEntered = true;
                            command = "Add";
                            invoker.executeCommand(command, type);
                            break;
                        case 2:
                            validCommandEntered = true;
                            command = "Remove";
                            invoker.executeCommand(command, type);
                            break;
                        case 3:
                            validCommandEntered = true;
                            command = "EditPrice";
                            invoker.executeCommand(command, type);
                            break;
                        case 4:
                            validCommandEntered = true;
                            command = "Display";
                            invoker.executeCommand(command, type);
                            break;
                        case 5:
                            validCommandEntered = true;
                            command = "TotalSales";
                            invoker.executeCommand(command, type);
                            break;
                        case 6:
                            mainMenu();
                            break;
                        case 7:
                            validCommandEntered = true;
                            command = "Exit";
                            invoker.executeCommand(command,type);
                            break;
                        default:
                            System.out.println("Invalid command. Please choose a valid command.");
                            break;
                    }
                } catch (InputMismatchException e) { // Handle case if mismatch in input value
                    System.out.println("Invalid input. Please enter a valid number (1-7).");
                    scanner.nextLine(); // Read next input
                }
            }
        }
    }

    public void salesinvoice() {
        subject.notifyObservers(Enums.EventType.SALES_INVOICE, "Opened Sales Invoice");
        System.out.println();

        invoker = new Invoker();

        while (true) {
            boolean validCommandEntered = false;

            while (!validCommandEntered) {
                Scanner scanner = new Scanner(System.in);

                System.out.println();
                System.out.println("##########################################################");
                System.out.println("                      Sales Invoice");
                System.out.println("##########################################################");
                System.out.println("----------------------------------------------------------");
                System.out.println("1: Create New Sales Invoice");
                System.out.println("----------------------------------------------------------");
                System.out.println("2: Remove Sales Invoice");
                System.out.println("----------------------------------------------------------");
                System.out.println("3: Insert to Sales Items Invoice");
                System.out.println("----------------------------------------------------------");
                System.out.println("4: Finalize Sale");
                System.out.println("----------------------------------------------------------");
                System.out.println("5: Display Sales Invoice");
                System.out.println("----------------------------------------------------------");
                System.out.println("6: Display Sales Items Invoice");
                System.out.println("----------------------------------------------------------");
                System.out.println("7: Go back to Main Menu");
                System.out.println("----------------------------------------------------------");
                System.out.println("8: Exit");
                System.out.println("----------------------------------------------------------");
                System.out.println("##########################################################");
                System.out.print("Please enter an option 1-8: ");

                String command;
                String type = "sales";

                //Each input will be using command pattern
                try {
                    int userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1:
                            validCommandEntered = true;
                            command = "CreateNewSalesInvoice";
                            invoker.executeCommand(command, type);
                            break;
                        case 2:
                            validCommandEntered = true;
                            command = "Remove";
                            invoker.executeCommand(command, type);
                            break;
                        case 3:
                            validCommandEntered = true;
                            command = "Add";
                            invoker.executeCommand(command,type);
                            break;
                        case 4:
                            validCommandEntered = true;
                            command = "EditPrice";
                            invoker.executeCommand(command,type);
                            break;
                        case 5:
                            validCommandEntered = true;
                            command = "Display";
                            invoker.executeCommand(command,type);
                            break;
                        case 6:
                            validCommandEntered = true;
                            command = "Display";
                            type = "sales_item";
                            invoker.executeCommand(command,type);
                            break;
                        case 7:
                            mainMenu();
                            break;
                        case 8:
                            validCommandEntered = true;
                            command = "Exit";
                            invoker.executeCommand(command,type);
                            break;
                        default:
                            System.out.println("Invalid command. Please choose a valid command.");
                            break;
                    }
                } catch (InputMismatchException e) { // Handle case if mismatch in input value
                    System.out.println("Invalid input. Please enter a valid number (1-8).");
                    scanner.nextLine(); // Read next input
                }
            }
        }
    }

    public void addItem(Item item) {
        inventoryEngine.addItem(item);
    }

    public List<Item> getInventory() {
        return inventoryEngine.getInventory();
    }

    public String getItemsList(){
        String result = inventoryEngine.getItemsList();
        return result;
    }

    public String getItemName(int SKU) {
        if (inventoryEngine.getItemName(SKU) != null) {
            return inventoryEngine.getItemName(SKU);
        }

        return "No Item by that SKU";
    }

    public void removeItemFromInventory(int SKU) {
        inventoryEngine.removeItemFromInventory(SKU);
    }

    public void updateSalesPrice(int SKU, double price){
        inventoryEngine.updateSalesPrice(SKU, price);
    }

    //will return total sales price (value of inventory at sale)
    public double getTotalValue() {
        return inventoryEngine.getTotalInventoryValue();
    }

    public void addCustomer(Customer customer) {
        customerEngine.addCustomer(customer);
    }

    public List<Customer> getCustomersList() {
        return customerEngine.getCustomersList();
    }

    public void removeCustomerByID(int customerID) {
        customerEngine.removeCustomerByID(customerID);
    }

    public String getCustomerName(int customerID) {
        return customerEngine.getCustomerName(customerID);
    }

    public String getCustomers(){
        String result = customerEngine.getCustomers();
        return result;
    }

    //TODO: use Invoice Factory
    public int newSalesInvoice() {
        SalesInvoice salesInvoice = new SalesInvoice();
        int newSalesInvoice = salesInvoiceEngine.newSalesInvoice();
        salesInvoice.setSalesInvoiceNumber(newSalesInvoice);

        return salesInvoice.getSalesInvoiceNumber();
    }

    public void deleteInvoice(int invoiceNumber) {
        salesInvoiceEngine.deleteInvoice(invoiceNumber);
    }

    public String getSalesInvoiceList() {
        String result = salesInvoiceEngine.getSalesInvoiceList();
        return result;
    }

    public void insertToSalesInvoice(int salesInvoiceNumber, int SKU, int quantity, int customerID) {
        salesInvoiceEngine.insertToSalesInvoice(salesInvoiceNumber, SKU, quantity, customerID);
    }

    public SalesInvoice getSalesInvoice(int salesInvoiceNumber) {
        return salesInvoiceEngine.getSalesInvoice(salesInvoiceNumber);
    }

    public String getSalesInvoiceItems(int invoiceNumber){
        String result = salesInvoiceEngine.getSalesInvoiceItems(invoiceNumber);
        return result;
    }

    public void finalizeSale(int invoiceNumber){
        salesInvoiceEngine.finalizeSale(invoiceNumber);
    }

}
