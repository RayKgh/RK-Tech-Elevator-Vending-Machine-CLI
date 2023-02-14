package com.techelevator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class UserInterface {

    /**
     * displayMainMenu() outputs the Main Menu to the console.
     */
    public void displayMainMenu() {
        System.out.printf("\n** MAIN MENU **\n" + "Please select from the following options:\n\n" + "(1) Display Vending Machine Items\n" +
                "(2) Purchase\n" +
                "(3) Exit\n\n");
    }

    /**
     * displayPurchasingMenu() displays the user's current balance and the purchasing menu
     * to the console.
      */
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
            System.out.printf("| %s | %-18s | $%s | %s |\n",
                    vendingMachineItem.getSlotID(), vendingMachineItem.getItemName(), vendingMachineItem.getItemPrice(),
                    vendingMachineItem.getCurrentInventory());

        }
        System.out.println("_______________________________________");
    }

    /**
     * The showVendingMachineChoices() receives a scanner and a List of vending machine
     * items as parameters. It calls the displayItems() method to print a list of the
     * vending machine items, their slot locations, price, and quantity remaining.
     * The Scanner reads the user's input to return to the Main Menu.     *
     */
    public void showVendingMachineChoices(Scanner scanner, List<Item> vendingMachineItems) {
        displayItems(vendingMachineItems);
        System.out.println("\nPress any key(s) + ENTER to return to Main Menu.");
        scanner.nextLine();
        displayMainMenu();
    }

    /**
     * waitOneSecond() generates a 1-second delay in execution. It is used to draw attention
     * to a UI message before subsequently printing the menu options.
     */
    public void waitOneSecond() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Used to prompt the user to select a valid menu option.
     */
    public void invalidInputPrompt() {
        System.out.println("Please select 1, 2, or 3.");
        waitOneSecond();
    }

    /**
     * displayMessage handles printing user prompts to the console.
     */
    public void displayMessage(String message) {
        System.out.println(message);
    }



}
