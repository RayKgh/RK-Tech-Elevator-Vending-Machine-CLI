package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

//    public void purchaseItem(String locationIdentifier) {
//        if (Integer.parseInt(itemsForPurchase.get(locationIdentifier).getCurrentInventory()) > 0) {
//            itemsForPurchase.get(locationIdentifier).getCurrentInventory()--;
//            amountSold++;
//        } else {
//            System.out.println("Product is sold out. Please select another option.");
//            // How to return to list of products?
//        }
//    }

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

    LocalDateTime now = LocalDateTime.now();
    final String dateTime = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

    public StringBuilder printToLog(String businessProcess, BigDecimal moneyInOrOut) {
        StringBuilder logEntry = new StringBuilder();
        logEntry.append(dateTime + " " + businessProcess + " $" + moneyInOrOut + " $" + getBalance());
        return logEntry;
    }

    public StringBuilder printToLog(String itemName, String locationIdentifier, BigDecimal itemPrice) {
        StringBuilder logEntry = new StringBuilder();
        logEntry.append(dateTime + " " + itemName + " " + locationIdentifier + " $" + itemPrice + " $" + getBalance());
        return logEntry;
    }

    public void generateSalesReport(List<Item> vendingMachineItems, File salesReport) {
        try (final PrintWriter sRWriter = new PrintWriter(salesReport);) {
            BigDecimal totalSales = new BigDecimal("0.00");
            for (Item item : vendingMachineItems) {
                sRWriter.printf("%-18s | %d\n", item.getItemName(), item.getAmountSold());
                totalSales.add(new BigDecimal(item.getAmountSold()).multiply(item.getItemPrice()));
            }
            sRWriter.println("**TOTAL SALES REVENUE** $" + totalSales);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
