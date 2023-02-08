package com.techelevator;

import java.math.BigDecimal;

public class Item {
    private String itemName;
    private BigDecimal itemPrice = new BigDecimal("");
    private int startingInventory;
    private int amountSold;
    private String itemType;

    final int MAX_QUANTITY = 5;

    public Item(String itemName, BigDecimal itemPrice, String itemType) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemType = itemType;
        startingInventory = MAX_QUANTITY;
        amountSold = 0;
    }

    public String getItemName() {
        return itemName;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public int getStartingInventory() {
        return startingInventory;
    }

    public int getAmountSold() {
        return amountSold;
    }

    public String getItemType() {
        return itemType;
    }

    public void printMessage() {
        if (itemType.equals("Chip")) {
            System.out.println("Crunch Crunch, Yum!");
        }else if (itemType.equals("Candy")) {
            System.out.println("Munch Munch, Yum!");
        }else if (itemType.equals("Drink")) {
            System.out.println("Glug Glug, Yum!");
        }else if (itemType.equals("Gum")) {
            System.out.println("Chew Chew, Yum!");
        }
    }
}
