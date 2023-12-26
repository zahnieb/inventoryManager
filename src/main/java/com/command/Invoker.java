package com.command;

import java.util.Map;
import java.util.HashMap;
import com.command.Command;
import com.manager.Inventory;
import com.manager.Item;

public class Invoker {
    private Map<String, Command> commands = new HashMap<>();
    private Item item;
    public Invoker(){
        commands.put("Add", new Add());
        commands.put("Remove", new Remove());
        commands.put("EditPrice", new Edit());
        commands.put("Display", new Display());
        commands.put("TotalSales", new GetTotalSales());
        commands.put("CreateNewSalesInvoice", new NewSalesInvoice());
        commands.put("Exit", new Exit());

    }
    public void executeCommand(String commandName, String type) {
        Command command = commands.get(commandName);
        if (command != null) {
            command.execute(type);
        }
        else {
            System.out.println("Invalid command");
        }
    }
}
