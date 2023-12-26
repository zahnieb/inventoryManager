package com.engines;

import com.Observer.Enums;
import com.database.ConnectDatabase;
import com.manager.Inventory;
import com.manager.Invoice;
import com.manager.InvoiceFactory;
import com.manager.SalesInvoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SalesInvoiceEngine {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;

    public SalesInvoiceEngine() {
        try {
            connection = new ConnectDatabase().getConnection();
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //returns new generated sales_invoice_number generated PK
    //source: https://stackoverflow.com/questions/7162989/sqlexception-generated-keys-not-requested-mysql
    public int newSalesInvoice() {
        //new invoice doesn't require SKU,or customer_id only default AI sales_invoice_number, total set to 0 initially

        try {
            int generatedInvoiceNo = 0;
            String query = "INSERT INTO SalesInvoice VALUES(default,0,null)";
            preparedStatement = connection.prepareStatement(query, statement.RETURN_GENERATED_KEYS);

            preparedStatement.executeUpdate();

            //save invoice number to save for next steps
            resultSet = preparedStatement.getGeneratedKeys();
            while (resultSet.next()) {
                //should only generate one invoice key
                generatedInvoiceNo = resultSet.getInt(1);
            }
            System.out.println();
            System.out.println("New Created Invoice Number: " + generatedInvoiceNo);

            String message = String.format("New Sales Invoice NO.%d was created.", generatedInvoiceNo);
            Inventory.subject.notifyObservers(Enums.EventType.CREATE_NEW_SALES_INVOICE, message);

            return generatedInvoiceNo;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void deleteInvoice(int salesInvoiceNumber) {
        String message = String.format("Invoice NO.%d was deleted.", salesInvoiceNumber);
        Inventory.subject.notifyObservers(Enums.EventType.DELETE_INVOICE, message);
        try {
            String query = "DELETE FROM SalesInvoice WHERE sales_invoice_number=" + salesInvoiceNumber;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

            System.out.println();

            System.out.println("Invoice " + salesInvoiceNumber + " Deleted Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public List<SalesInvoice> getSalesInvoiceList() {
//        try {
//            InvoiceFactory invoiceFactory = new InvoiceFactory();
//            List<SalesInvoice> invoiceList = new ArrayList<>();
//            String query = "SELECT * FROM SalesInvoice";
//            preparedStatement = connection.prepareStatement(query);
//            resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                SalesInvoice tempInvoice = new SalesInvoice();
//
//                //will never be null
//                tempInvoice.setSalesInvoiceNumber(resultSet.getInt(1));
//                tempInvoice.setTotal(resultSet.getDouble(2));
//
//                //check for null values on not required columns
//                //source:https://stackoverflow.com/questions/2920364/checking-for-a-null-int-value-from-a-java-resultset
//                int customerID = resultSet.getInt(3);
//                if (!resultSet.wasNull()) {
//                    tempInvoice.setCustomerID(customerID);
//                }
//
//                invoiceList.add(tempInvoice);
//            }
//
//            return invoiceList;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }

    public String getSalesInvoiceList() {
        String message = "Displayed Sales Invoice List";
        Inventory.subject.notifyObservers(Enums.EventType.DISPLAY_INVOICE, message);
        StringBuilder result = new StringBuilder();
        try {
            InvoiceFactory invoiceFactory = new InvoiceFactory();
            List<SalesInvoice> invoiceList = new ArrayList<>();
            String query = "SELECT * FROM SalesInvoice";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            int invoiceNumberWidth = 15;
            int totalWidth = 15;
            int customerIDWidth = 15;

            String border = "+" +
                    "-".repeat(invoiceNumberWidth + 2) + "+" +
                    "-".repeat(totalWidth + 2) + "+" +
                    "-".repeat(customerIDWidth + 2) + "+";;

            // Formatting the header
            result.append(border).append("\n");
            result.append(String.format("| %-"+invoiceNumberWidth+"s | %-"+totalWidth+"s | %-"+customerIDWidth+"s |\n",
                    "Invoice Number", "Total", "Customer ID"));
            result.append(border).append("\n");

            while (resultSet.next()) {
                SalesInvoice tempInvoice = new SalesInvoice();
                tempInvoice.setSalesInvoiceNumber(resultSet.getInt(1));
                tempInvoice.setTotal(resultSet.getDouble(2));

                // Check for null values
                int customerID = resultSet.getInt(3);
                String customerIDStr = resultSet.wasNull() ? "" : String.valueOf(customerID);

                result.append(String.format("| %-"+invoiceNumberWidth+"d | %-"+totalWidth+".2f | %-"+customerIDWidth+"s |\n",
                        tempInvoice.getSalesInvoiceNumber(), tempInvoice.getTotal(), customerIDStr));

                invoiceList.add(tempInvoice);
            }
            result.append(border);
            System.out.println(result);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //5 queries (may split to different methods for each later)
    //updates SalesInvoice based on parameters
    public void insertToSalesInvoice(int salesInvoiceNumber, int SKU, int quantity, int customerID) {
        String message = "Inserted updated sales information into Sales Items Invoice.";
        Inventory.subject.notifyObservers(Enums.EventType.ADD_TO_SALES_INVOICE, message);
        try {
            //leave subtotal null & calculate in query 2
            String query1 = "INSERT INTO Sales_to_Item VALUES(?,?,?, null,default)";
            preparedStatement = connection.prepareStatement(query1);
            //set FK(sales_Invoice_number), quantity, PK(sales_items_id=default(AI))
            preparedStatement.setInt(1, salesInvoiceNumber);
            preparedStatement.setInt(2, SKU);
            preparedStatement.setInt(3, quantity);

            preparedStatement.executeUpdate();

            //update subtotal = (quantity * Item sales_price)
            String query2 = "UPDATE Sales_to_Item SET subtotal = " +
                    "(quantity * (SELECT sales_price FROM Item WHERE SKU=sales_to_item.SKU))" +
                    "WHERE sales_invoice_number=" + salesInvoiceNumber;
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.executeUpdate();

            //update invoice Total
            String query3 = "UPDATE SalesInvoice " +
                    "SET total = (" +
                    "SELECT SUM(subtotal) FROM sales_to_item " +
                    "WHERE sales_to_item.sales_invoice_number = SalesInvoice.sales_invoice_number" +
                    ") WHERE sales_invoice_number=" + salesInvoiceNumber;
            preparedStatement = connection.prepareStatement(query3);

            preparedStatement.executeUpdate();

            //add SKU to Invoice **NO SKU NEEDED IN INVOICE as Item_toSale has connection to Items, need to list
            //all item_to_sale associated with that invoice number
            /*
            String query4 = "UPDATE SalesInvoice SET SKU = (" +
                    "SELECT SKU FROM sales_to_item " +
                    "WHERE sales_to_item.sales_invoice_number = SalesInvoice.sales_invoice_number" +
                    ") WHERE sales_invoice_number=6";
            preparedStatement = connection.prepareStatement(query4);
            preparedStatement.executeUpdate();
            */

            //update customer_id for salesInvoice
            String query4 = "UPDATE SalesInvoice SET customer_id = ? "+
                    " WHERE sales_invoice_number= ?" ;
            preparedStatement = connection.prepareStatement(query4);
            preparedStatement.setInt(1,customerID);
            preparedStatement.setInt(2,salesInvoiceNumber);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public SalesInvoice getSalesInvoice(int salesInvoiceNumber) {

        try {
            String query = "SELECT * FROM SalesInvoice WHERE sales_invoice_number=" + salesInvoiceNumber;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                SalesInvoice tempInvoice = new SalesInvoice();
                tempInvoice.setSalesInvoiceNumber(resultSet.getInt(1));
                tempInvoice.setTotal(resultSet.getDouble(2));

                int customerID = resultSet.getInt(3);
                if (!resultSet.wasNull()) {
                    tempInvoice.setCustomerID(customerID);
                }

                return tempInvoice;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String getSalesInvoiceItems(int invoiceNumber){

        String message = "Displayed Sales Invoice Items";
        Inventory.subject.notifyObservers(Enums.EventType.DISPLAY_ITEMS_INVOICE, message);

        StringBuilder result = new StringBuilder();
        try {
            String query = "SELECT Item.name, Item.sku, Sales_to_item.quantity, item.sales_price, Sales_to_item.subtotal " +
                    "FROM Sales_to_Item INNER JOIN Item ON Item.sku=sales_to_item.sku " +
                    "WHERE sales_to_item.sales_invoice_number=" + invoiceNumber;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            System.out.println();

            int nameWidth = 20;
            int skuWidth = 10;
            int quantityWidth = 10;
            int salesPriceWidth = 19;
            int subtotalWidth = 20;

            String border = "+----+" +
                    "-".repeat(nameWidth) + "+" +
                    "-".repeat(skuWidth) + "+" +
                    "-".repeat(quantityWidth) + "+" +
                    "-".repeat(salesPriceWidth) + "+" +
                    "-".repeat(subtotalWidth) + "+";

            result.append(border).append("\n");
            result.append(String.format("|%-4s|%-"+nameWidth+"s|%-"+skuWidth+"s|%-"+quantityWidth+"s|%-"+salesPriceWidth+"s|%-"+subtotalWidth+"s|\n",
                    "Row","Item Name", "SKU", "Quantity", "Sales Price", "Subtotal"));
            result.append(border).append("\n");


            int row = 0;
            while (resultSet.next()){
                row += 1;
                String name = resultSet.getString(1);
                int SKU = resultSet.getInt(2);
                int quantity = resultSet.getInt(3);
                double salesPrice = resultSet.getDouble(4);
                double subTotal = resultSet.getDouble(5);

                result.append(String.format("|%-4d|%-"+nameWidth+"s|%-"+skuWidth+"d|%-"+quantityWidth+"d|%-"+salesPriceWidth+".2f|%-"+subtotalWidth+".2f|\n",
                        row, name, SKU, quantity, salesPrice, subTotal));
            }
            result.append(border);
            System.out.println(result.toString());
            return result.toString();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //update customer purchased total
    public void finalizeSale(int salesInvoiceNumber){
        String message = String.format("Sales were finalized for Invoice NO.%d", salesInvoiceNumber);
        Inventory.subject.notifyObservers(Enums.EventType.FINALIZE_SALE, message);

        double total = 0;
        int customerID = 0;
        try {
            //Get total from Final Invoice
            String query = "SELECT total, customer_id FROM SalesInvoice WHERE sales_invoice_number=" + salesInvoiceNumber;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            //should only loop once as InvoiceNumber is unique
            while(resultSet.next()) {
                total = resultSet.getDouble(1);
                customerID = resultSet.getInt(2);
            }
            //update customer total purchase history
            String query2 = "UPDATE customer SET total_purchases = total_purchases + ?  WHERE customer_id= ?";
            preparedStatement = connection.prepareStatement(query2);
            preparedStatement.setDouble(1, total);
            preparedStatement.setInt(2, customerID);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
