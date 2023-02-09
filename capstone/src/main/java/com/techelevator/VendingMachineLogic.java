package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachineLogic {

    List<Item> vendingMachineItems = new ArrayList<>();


    public List<Item> StockVendingMachine(File vendingMachineStock) {
        try (Scanner fileReader = new Scanner(vendingMachineStock)) {
            while (fileReader.hasNextLine()) {
                String[] itemAttributes = fileReader.nextLine().split("\\|");
                BigDecimal itemPrice = new BigDecimal(itemAttributes[2]);
                Item item = new Item(itemAttributes[0], itemAttributes[1], itemPrice, itemAttributes[3]);
                vendingMachineItems.add(item);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return vendingMachineItems;
    }

    public void displayItems() {
        System.out.println("Loc.     Item               Price  Qty");
        for (Item vendingMachineItem : vendingMachineItems) {
            System.out.printf("%s | %-20s | $%s | %s\n",vendingMachineItem.getSlotIdentifier(), vendingMachineItem.getItemName(), vendingMachineItem.getItemPrice(),vendingMachineItem.getCurrentInventory());

        }
    }

}
