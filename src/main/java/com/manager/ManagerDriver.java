package com.manager;

public class ManagerDriver {
    public static void main(String[] args) {
        start();
    }

    private static void start() {
        Inventory inventory = new Inventory();
        inventory.startMenu();
    }
}
