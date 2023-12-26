package com.database;

import java.sql.*;
import java.lang.*;

//connect to DB Source: https://www.vogella.com/tutorials/MySQLJava/article.html
public class ConnectDatabase {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    static final String url = "jdbc:mysql://localhost:3306/inventory";
    static String user;
    static String password;
    static final String driver = "com.mysql.cj.jdbc.Driver";

    //connect Database
    public ConnectDatabase() {
        try {

            this.user = "user";
            this.password = "csci4448";

            try {
                Class.forName(this.driver);
                connect = DriverManager.getConnection(this.url, this.user, this.password);
            } catch (Exception e){
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //get connection
    public Connection getConnection(){
        try{
            Class.forName(driver);
            connect = DriverManager.getConnection(this.url, this.user, this.password);
            //System.out.println("Connection to DB Successful.");
        } catch (Exception e){
            e.printStackTrace();
        }
        return this.connect;
    }

    //close resultSet
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }
            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
}
