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

    /**
     * The dispenseChange() method checks if the user's changeToReturn is zero. If changeToReturn is non-zero, it
     * calculates the change in the smallest number of coins using quarters, dimes, and nickels.
     */

    public String dispenseChange(BigDecimal changeToReturn) {
        String changeOutput = "Please collect your change: ";
        BigDecimal QUARTER = new BigDecimal("0.25");
        BigDecimal DIME = new BigDecimal("0.10");
        BigDecimal NICKEL = new BigDecimal("0.05");

        if (changeToReturn.compareTo(BigDecimal.ZERO) == 0) {
            return changeOutput + "$0.00";
        }
        if (changeToReturn.compareTo(QUARTER) >= 0) {
            BigDecimal numberOfQuarters = changeToReturn.divideToIntegralValue(QUARTER);
            changeOutput += String.format("\n -> %s Quarter(s)", numberOfQuarters);
            changeToReturn = changeToReturn.subtract(QUARTER.multiply(numberOfQuarters));
        }
        if (changeToReturn.compareTo(DIME) >= 0) {
            BigDecimal numberOfDimes = changeToReturn.divideToIntegralValue(DIME);
            changeOutput += String.format("\n -> %s Dime(s)", numberOfDimes);
            changeToReturn = changeToReturn.subtract(DIME.multiply(numberOfDimes));
        }
        if (changeToReturn.compareTo(NICKEL) >= 0) {
            BigDecimal numberOfNickels = changeToReturn.divideToIntegralValue(NICKEL);
            changeOutput += String.format("\n -> %s Nickel(s)", numberOfNickels);
        }
        updateBalance(balance);
        return changeOutput;
    }

}
