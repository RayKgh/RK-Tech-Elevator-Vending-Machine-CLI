package com.techelevator;

import java.math.BigDecimal;

public class Item {
    private final String slotIdentifier;
    private final String itemName;
    private final BigDecimal itemPrice;
    private int currentInventory;
    private int amountSold;
    private final String itemType;

    final int MAX_QUANTITY = 5;

    public Item(String slotIdentifier, String itemName, BigDecimal itemPrice, String itemType) {
        this.slotIdentifier = slotIdentifier;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemType = itemType;
        currentInventory = MAX_QUANTITY;
        amountSold = 0;
    }

    public String getSlotIdentifier() {
        return slotIdentifier;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public String getCurrentInventory() {
        if (currentInventory == 0) {
            return "SOLD OUT";
        }
        return String.valueOf(currentInventory);
    }

    public void purchase() {
        if (currentInventory > 0) {
            currentInventory--;
            amountSold++;
        } else {
            System.out.println("Product is sold out. Please select another option.");
            // How to return to list of products?
        }
    }

    public int getAmountSold() {
        return amountSold;
    }

    public String getItemType() {
        return itemType;
    }

    public void printMessage() {
        switch (itemType) {
            case "Chip":
                System.out.println("Crunch Crunch, Yum!");
                break;
            case "Candy":
                System.out.println("Munch Munch, Yum!");
                break;
            case "Drink":
                System.out.println("Glug Glug, Yum!");
                break;
            case "Gum":
                System.out.println("Chew Chew, Yum!");
                break;
        }
    }
}
