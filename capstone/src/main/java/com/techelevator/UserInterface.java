package com.techelevator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class UserInterface {

    // displays main menu
    public void displayMainMenu() {
        System.out.printf("\n** MAIN MENU **\n" + "Please select from the following options:\n\n" + "(1) Display Vending Machine Items\n" +
                "(2) Purchase\n" +
                "(3) Exit\n\n");
    }
    // displays purchase submenu; balance display should update when value is changed
    public void displayPurchasingMenu(BigDecimal balance) {
        System.out.printf("\nCurrent Money Provided: $%s\n" +
                "(1) Feed Money\n" +
                "(2) Select Product\n" +
                "(3) Finish Transaction\n\n", balance);
    }


    // shows user a list of the items on sale, their location in the machine, their price, and how many of each is in stock
    public void displayItems(List<Item> vendingMachineItems) {
        System.out.println("_______________________________________");
        System.out.println("|Slot|        Item        | Price |Qty|");
        System.out.println("_______________________________________");
        for (Item vendingMachineItem : vendingMachineItems) {
            System.out.printf("| %s | %-18s | $%s | %s |\n", vendingMachineItem.getSlotIdentifier(), vendingMachineItem.getItemName(), vendingMachineItem.getItemPrice(), vendingMachineItem.getCurrentInventory());

        }
        System.out.println("_______________________________________");
    }

    public void showVendingMachineChoices(Scanner scanner, List<Item> vendingMachineItems) {
        displayItems(vendingMachineItems);
        System.out.println("\nPress any key to return to Main Menu.");
        scanner.nextLine();
        displayMainMenu();
    }

    public void waitOneSecond() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void invalidInputPrompt() {
        System.out.println("Please select 1, 2, or 3.");
        waitOneSecond();
    }



}
