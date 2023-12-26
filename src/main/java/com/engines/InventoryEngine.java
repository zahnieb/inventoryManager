package com.engines;

import com.database.ConnectDatabase;
import com.manager.Inventory;
import com.manager.Item;
import com.Observer.Enums;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/*
::::::: MySQL interaction With Items/Inventory:::::::::::
 */
public class InventoryEngine {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;

    Inventory inventory;

    public InventoryEngine() {
        try {
            connection = new ConnectDatabase().getConnection();
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //add one item to db
    public void addItem(Item item) {
        try {
            String query = "INSERT INTO item VALUES(?,?,?,?,default)";
            preparedStatement = connection.prepareStatement(query);
            //SKU defaulted by DB increments of 1
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getDescription());
            preparedStatement.setDouble(3, item.getPurchasePrice());
            preparedStatement.setDouble(4, item.getSalesPrice());

            preparedStatement.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Item Added");
            //System.out.println("Item Added");
            String message = String.format("%s %s was added to inventory.", "Item", item.getName());
            Inventory.subject.notifyObservers(Enums.EventType.ADD_ITEM, message);
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //get inventory, will create items into list
    public List<Item> getInventory() {
        List<Item> itemsList = new ArrayList<>();

        try {
            String query = "SELECT * FROM Item";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            //creates items to add to list per result row
            while(resultSet.next()){
                Item tempItem = new Item();
                tempItem.setName(resultSet.getString(1));
                tempItem.setDescription(resultSet.getString(2));
                tempItem.setPurchasePrice(resultSet.getDouble(3));
                tempItem.setSalesPrice(resultSet.getDouble(4));
                tempItem.setSKU(resultSet.getInt(5));

                itemsList.add(tempItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return itemsList;
    }

    //get SKU of item by name
    public List<Integer> getSKU(String name) {
        List<Integer> result = new ArrayList<Integer>();

        try {
            String query = "SELECT SKU FROM Item WHERE name =" + name;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                result.add(resultSet.getInt(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public String getItemName(int SKU) {
        try {
            String query = "SELECT name FROM Item WHERE SKU =" + SKU;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                return resultSet.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public void removeItemFromInventory(int SKU){
        try {
            String query = "DELETE FROM Item WHERE SKU=" + SKU;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

            System.out.println("Removed Item From Inventory");
            System.out.println();
        } catch (Exception e) {
            System.out.println("There was an Error Removing Item From Inventory");
            e.printStackTrace();
        }
        String message = String.format("%s with SKU %d was removed from inventory.", "Item", SKU);
        Inventory.subject.notifyObservers(Enums.EventType.DELETE_ITEM, message);
    }

    //Source: https://www.scaler.com/topics/mysql-sum/
    public Double getTotalInventoryValue(){
        try {
            String query = "SELECT SUM(sales_price) AS 'Total' FROM Item;";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                String message = String.format("Total Inventory Sales Price Value: $%.2f", resultSet.getDouble(1));
                Inventory.subject.notifyObservers(Enums.EventType.GET_TOTAL_SALES_PRICE_VALUE, message);
                return resultSet.getDouble(1);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return 0.0;
    }

    public void updateSalesPrice(int SKU, double price){
        try {
            String query = "UPDATE Item SET sales_price = " + price + " WHERE SKU = " + SKU;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

            System.out.println("Updated Item's Sales Price in Inventory");
        } catch (Exception e) {
            System.out.println("There was an Error updating Item's Sales Price in Inventory");
            e.printStackTrace();
        }
        String message = String.format("%s with SKU %d updated with new sales price: $%.2f", "Item", SKU, price);
        Inventory.subject.notifyObservers(Enums.EventType.UPDATE_SALES_PRICE, message);
    }

    public String getItemsList() {
        String message = "User displayed inventory";
        Inventory.subject.notifyObservers(Enums.EventType.DISPLAY_INVENTORY, message);
        StringBuilder result = new StringBuilder();
        try {
            String query = "SELECT name, description, purchase_price, sales_price, SKU FROM item";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            System.out.println();

            // Determine the width of each column, ensuring that it's enough for the header and the data
            int nameWidth = 20;
            int descriptionWidth = 30;
            int purchasePriceWidth = 19;
            int salesPriceWidth = 19;
            int skuWidth = 10;

            // Construct the border string based on the column widths
            String border = "+----+" +
                    "-".repeat(nameWidth) + "+" +
                    "-".repeat(descriptionWidth) + "+" +
                    "-".repeat(purchasePriceWidth) + "+" +
                    "-".repeat(salesPriceWidth) + "+" +
                    "-".repeat(skuWidth) + "+";

            // Formatting the header with vertical separators
            result.append(border).append("\n");
            result.append(String.format("|%-4s|%-"+nameWidth+"s|%-"+descriptionWidth+"s|%-"+purchasePriceWidth+"s|%-"+salesPriceWidth+"s|%-"+skuWidth+"s|\n",
                    "Row", "Item Name", "Description", "Purchase Price", "Sales Price", "SKU"));
            result.append(border).append("\n");

            int row = 0;
            while (resultSet.next()) {
                row += 1;
                String name = resultSet.getString(1);
                String description = resultSet.getString(2);
                double purchasePrice = resultSet.getDouble(3);
                double salesPrice = resultSet.getDouble(4);
                int sku = resultSet.getInt(5);

                // Formatting each row of data with vertical separators
                result.append(String.format("|%-4d|%-"+nameWidth+"s|%-"+descriptionWidth+"s|%-"+purchasePriceWidth+".2f|%-"+salesPriceWidth+".2f|%-"+skuWidth+"d|\n",
                        row, name, description, purchasePrice, salesPrice, sku));
            }
            result.append(border);

            System.out.println(result.toString());
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }




}
