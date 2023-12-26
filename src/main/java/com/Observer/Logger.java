package com.Observer;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;

import com.manager.Inventory;


public class Logger extends Observer {

    private String event;
    Subject subject;
    private boolean writeAvailable;
    private String fileName = "logger";
    private static Logger uniqueLogger;

    private Logger() {
        this.subject = Inventory.subject;
        this.subject.addObserver(this);
        writeAvailable = false;//set false until a file connection is set up
    }

    //Singleton Pattern (Lazy instantiation)
    public synchronized static Logger getLogger() {
        if (uniqueLogger == null) { //using lazy instantiation with double check locking
            synchronized (Logger.class) {
                if (uniqueLogger == null) {
                    uniqueLogger = new Logger();
                    uniqueLogger.clearLogFile();
                }
            }
        }
        return uniqueLogger;
    }

    public void update(Enums.EventType eventType, String event) {

        if (Enums.EventType.START_MENU == eventType || Enums.EventType.CUSTOMER_LIST == eventType || Enums.EventType.INVENTORY == eventType || Enums.EventType.SALES_INVOICE == eventType || Enums.EventType.ADD_ITEM == eventType || Enums.EventType.DELETE_ITEM == eventType || Enums.EventType.UPDATE_SALES_PRICE == eventType || Enums.EventType.DISPLAY_INVENTORY == eventType || Enums.EventType.GET_TOTAL_SALES_PRICE_VALUE == eventType || Enums.EventType.ADD_CUSTOMER == eventType || Enums.EventType.REMOVE_CUSTOMER == eventType || Enums.EventType.DISPLAY_CUSTOMER == eventType || Enums.EventType.CREATE_NEW_SALES_INVOICE == eventType || Enums.EventType.DELETE_INVOICE == eventType || Enums.EventType.DISPLAY_INVOICE == eventType || Enums.EventType.DISPLAY_ITEMS_INVOICE == eventType || Enums.EventType.ADD_TO_SALES_INVOICE == eventType || Enums.EventType.FINALIZE_SALE == eventType || Enums.EventType.EXIT_PROGRAM == eventType) {
            createFile(fileName);
            this.event = eventType + ": " + event;
            if (writeAvailable)
                write();
        }
    }

    private void clearLogFile() {
        try {
            // Using the same file path as in other parts of your Logger class
            //File myObj = new File("loggerFiles\\" + fileName);
            File myObj = new File(fileName);
            if (myObj.getParentFile() != null) {
                myObj.getParentFile().mkdirs(); // Create directories if they don't exist
            }

            // Overwrite the file (clears it if it exists, creates it if it doesn't)
            FileWriter fw = new FileWriter(myObj, false);
            fw.close();

            writeAvailable = true; // Now ready to write to the file
        } catch (IOException e) {
            System.out.println("An error occurred clearing the logger file.");
            writeAvailable = false;
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////
    //////   logger file handling    ///////////////////////////////////////
    //Creating a file: https://www.w3schools.com/java/java_files_create.asp
    private void createFile(String fileName) {
        try {
            //File myObj = new File("loggerFiles\\" + fileName);
            File myObj = new File(fileName);
            if (!myObj.isFile()) {
                // Create a new file if it doesn't exist
                myObj.getParentFile().mkdirs();
                if (myObj.createNewFile()) {
                    System.out.println("Logger file created: " + myObj.getName());
                }
            }
            writeAvailable = true;
        } catch (IOException e) {
            System.out.println("An error occurred accessing the logger file.");
            writeAvailable = false;
            e.printStackTrace();
        }
    }


    //Writing to a file: https://www.w3schools.com/java/java_files_create.asp
    //and https://www.baeldung.com/java-append-to-file
    private void write() {
        try {
            //FileWriter writer = new FileWriter("loggerFiles\\" + fileName, true); //true will append to the file
            FileWriter writer = new FileWriter(fileName, true);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(event);
            bufferWriter.newLine();
            bufferWriter.close();
        } catch (IOException e) {
            writeAvailable = false;
            System.out.println("An error occurred writing to the logger file.");
            e.printStackTrace();
        }

    }
}