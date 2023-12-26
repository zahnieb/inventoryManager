package com.engines;

import com.Observer.Enums;
import com.database.ConnectDatabase;
import com.manager.Customer;
import com.manager.Inventory;
import com.manager.Item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerEngine {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private PreparedStatement preparedStatement = null;

    public CustomerEngine() {
        try {
            connection = new ConnectDatabase().getConnection();
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addCustomer(Customer customer) {
        try {
            String query = "INSERT INTO customer VALUES(?,?,?,default)";
            preparedStatement = connection.prepareStatement(query);
            //default customer_id (AI)
            preparedStatement.setString(1, customer.getCustomerName());
            preparedStatement.setString(2, customer.getPhone());
            preparedStatement.setDouble(3, customer.getTotalPurchases());

            preparedStatement.executeUpdate();
            //JOptionPane.showMessageDialog(null, "Item Added");
            System.out.println("Customer Added");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String message = String.format("%s %s was added.", "Customer", customer.getCustomerName());
        Inventory.subject.notifyObservers(Enums.EventType.ADD_CUSTOMER, message);
    }

    public void removeCustomerByID(int customerID) {
        try {
            String query = "DELETE FROM Customer WHERE customer_id=" + customerID;
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();

            System.out.println("Removed Customer From Customer Table");
            System.out.println();
        } catch (Exception e) {
            System.out.println("There was an error Remove customer");
            e.printStackTrace();
        }
        String message = String.format("%s ID %d was removed.", "Customer", customerID);
        Inventory.subject.notifyObservers(Enums.EventType.REMOVE_CUSTOMER, message);
    }

    public List<Customer> getCustomersList() {
        List<Customer> customersList = new ArrayList<>();

        try {
            String query = "SELECT * FROM Customer";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            //creates items to add to list per result row
            while (resultSet.next()) {
                Customer tempItem = new Customer();
                tempItem.setCustomerName(resultSet.getString(1));
                tempItem.setPhone(resultSet.getString(2));
                tempItem.setTotalPurchase(resultSet.getDouble(3));
                tempItem.setCustomerID(resultSet.getInt(4));

                customersList.add(tempItem);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return customersList;
    }

    public String getCustomerName(int customerID) {
        try {
            String query = "SELECT customer_name FROM Customer WHERE customer_id =" + customerID;
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (Exception e) {
            System.out.println("There was an Error retrieving Customer Name");
            e.printStackTrace();
        }

        return null;
    }

    public String getCustomers() {
        String message = "User displayed customer list";
        Inventory.subject.notifyObservers(Enums.EventType.DISPLAY_CUSTOMER, message);
        StringBuilder result = new StringBuilder();
        try {
            String query = "SELECT customer_name, phone, total_purchases, customer_id FROM customer";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            System.out.println();

            // Adjusting the border to include the vertical separators
            String border = "+----+--------------------+---------------+-----------------+------------+";

            // Formatting the header with vertical separators
            result.append(border).append("\n");
            result.append(String.format("|%-4s|%-20s|%-15s|%-17s|%-12s|\n", "Row", "Customer Name", "Phone", "Total Purchases", "Customer ID"));
            result.append(border).append("\n");

            int row = 0;
            while (resultSet.next()) {
                row += 1;
                String customerName = resultSet.getString(1);
                String phone = resultSet.getString(2);
                double totalPurchases = resultSet.getDouble(3);
                int customerId = resultSet.getInt(4);

                // Formatting each row of data with vertical separators
                result.append(String.format("|%-4d|%-20s|%-15s|%-17.2f|%-12d|\n", row, customerName, phone, totalPurchases, customerId));
            }
            result.append(border);

            System.out.println(result);
            System.out.println();
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
