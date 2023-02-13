package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachine {

    private BigDecimal balance = new BigDecimal("0.00");

    public BigDecimal getBalance() {
        return balance;
    }

    public void updateBalance(BigDecimal itemPrice) {
        balance = balance.subtract(itemPrice);
    }

    public void feedMoney(BigDecimal dollarAmount) {
        balance = balance.add(dollarAmount);
    }

    List<Item> vendingMachineItems = new ArrayList<>();

    Map<String, Item> itemsForPurchase = new HashMap<>();

    /**
    The StockVendingMachine() method runs when the vending machine starts and accepts a CSV input file as a parameter.
    It reads the item information from the file and creates both a List<Item> and a Map<String, Item> of the items.
     */
    private final File vendingMachineStock = new File("vendingmachine.csv");
    public void StockVendingMachine() {
        try (Scanner fileReader = new Scanner(vendingMachineStock)) {
            while (fileReader.hasNextLine()) {
                String[] itemAttributes = fileReader.nextLine().split("\\|");
                BigDecimal itemPrice = new BigDecimal(itemAttributes[2]);
                Item item = new Item(itemAttributes[0], itemAttributes[1], itemPrice, itemAttributes[3]);
                vendingMachineItems.add(item);
                itemsForPurchase.put(item.getSlotID(), item);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    // calculates and dispenses "coins" as the user's "change"
    public String dispenseChange(BigDecimal balance) {
        String changeOutput = "Please collect your change: ";
        BigDecimal QUARTER = new BigDecimal("0.25");
        BigDecimal DIME = new BigDecimal("0.10");
        BigDecimal NICKEL = new BigDecimal("0.05");

        if (balance.compareTo(BigDecimal.ZERO) == 0) {
            return changeOutput + "$0.00";
        }
        while (balance.compareTo(BigDecimal.ZERO) > 0) {
            if (balance.compareTo(QUARTER) >= 0) {
                BigDecimal numberOfQuarters = balance.divideToIntegralValue(QUARTER);
                changeOutput += String.format("\n -> %s Quarter(s)", numberOfQuarters);
                balance = balance.subtract(QUARTER.multiply(numberOfQuarters));
            } else if (balance.compareTo(DIME) >= 0) {
                BigDecimal numberOfDimes = balance.divideToIntegralValue(DIME);
                changeOutput += String.format("\n -> %s Dime(s)", numberOfDimes);
                balance = balance.subtract(DIME.multiply(numberOfDimes));
            } else if (balance.compareTo(NICKEL) >= 0) {
                BigDecimal numberOfNickels = balance.divideToIntegralValue(NICKEL);
                changeOutput += String.format("\n -> %s Nickel(s)", numberOfNickels);
                balance = balance.subtract(NICKEL.multiply(numberOfNickels));
            }
        }
        return changeOutput;
    }

}
