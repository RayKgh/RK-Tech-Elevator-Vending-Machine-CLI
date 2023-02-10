package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

public class VendingMachine {

    private BigDecimal balance = new BigDecimal("0.00");

    public BigDecimal getBalance() {
        return balance;
    }

    public void updateBalance(BigDecimal itemPrice) {
        balance = balance.subtract(itemPrice);
    }

    public void feedMoney(String dollars) {
        balance = balance.add(new BigDecimal(dollars));
    }


    UserInterface ui = new UserInterface();
    List<Item> vendingMachineItems = new ArrayList<>();

    Map<String, Item> itemsForPurchase = new HashMap<>();

    /*
    The StockVendingMachine() method runs when the vending machine starts and accepts a CSV input file as a parameter.
    It reads the item information from the file and creates both a List<Item> and a Map<String, Item> of the items.
     */
    public void StockVendingMachine(File vendingMachineStock) {
        try (Scanner fileReader = new Scanner(vendingMachineStock)) {
            while (fileReader.hasNextLine()) {
                String[] itemAttributes = fileReader.nextLine().split("\\|");
                BigDecimal itemPrice = new BigDecimal(itemAttributes[2]);
                Item item = new Item(itemAttributes[0], itemAttributes[1], itemPrice, itemAttributes[3]);
                vendingMachineItems.add(item);
                itemsForPurchase.put(item.getSlotIdentifier(), item);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    // shows user a list of the items on sale, their location in the machine, their price, and how many of each is in stock
    public void displayItems() {
        System.out.println("_______________________________________");
        System.out.println("|Slot|        Item        | Price |Qty|");
        System.out.println("_______________________________________");
        for (Item vendingMachineItem : vendingMachineItems) {
            System.out.printf("| %s | %-18s | $%s | %s |\n", vendingMachineItem.getSlotIdentifier(), vendingMachineItem.getItemName(), vendingMachineItem.getItemPrice(), vendingMachineItem.getCurrentInventory());

        }
        System.out.println("_______________________________________");
    }
    // calculates and dispenses "coins" as the user's "change"
    public StringBuilder dispenseChange(BigDecimal balance) {
        StringBuilder changeOutput = new StringBuilder("Please collect your change: ");
        BigDecimal QUARTER = new BigDecimal("0.25");
        BigDecimal DIME = new BigDecimal("0.10");
        BigDecimal NICKEL = new BigDecimal("0.05");

        if (balance.compareTo(BigDecimal.ZERO) == 0) {
            return changeOutput.append("$0.00");
        }
        while (balance.compareTo(BigDecimal.ZERO) > 0) {
            if (balance.compareTo(QUARTER) >= 0) {
                changeOutput.append("\nQuarter");
                balance = balance.subtract(QUARTER);
            } else if (balance.compareTo(DIME) >= 0) {
                changeOutput.append("\nDime");
                balance = balance.subtract(DIME);
            } else if (balance.compareTo(NICKEL) >= 0) {
                changeOutput.append("\nNickel");
                balance = balance.subtract(NICKEL);
            }
        }
        return changeOutput;
    }

    public StringBuilder printToLog(String businessProcess, BigDecimal moneyInOrOut) {
        StringBuilder logEntry = new StringBuilder();
        logEntry.append(LocalDateTime.now() + " " + businessProcess + " $" + moneyInOrOut + " $" + getBalance());
        return logEntry;
    }

    public StringBuilder printToLog(String itemName, String locationIdentifier, BigDecimal itemPrice) {
        StringBuilder logEntry = new StringBuilder();
        logEntry.append(LocalDateTime.now() + " " + itemName + " $" + locationIdentifier + " " + itemPrice + " $" + getBalance());
        return logEntry;
    }

}
