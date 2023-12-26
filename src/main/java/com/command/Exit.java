package com.command;

import com.Observer.Enums;
import com.manager.Inventory;
import com.manager.Item;
import java.util.Scanner;


public class Exit implements Command{

    public Exit(){};

    @Override
    public void execute(String type) {
        String message = "Exited Program";
        Inventory.subject.notifyObservers(Enums.EventType.EXIT_PROGRAM, message);
        System.out.println("Exiting Program...");
        System.exit(0);
    }


}