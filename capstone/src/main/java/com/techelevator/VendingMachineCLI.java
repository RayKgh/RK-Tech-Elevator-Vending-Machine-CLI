package com.techelevator;

import java.io.File;
import java.util.Scanner;

public class VendingMachineCLI {
    Scanner scanner = new Scanner(System.in);

    final UserInterface ui = new UserInterface();
    final VendingMachineLogic vml = new VendingMachineLogic();
    final File vendingMachineStock = new File("vendingmachine.csv");


    public void run() {
        // entry point for the vending machine
        ui.displayMainMenu();
        vml.StockVendingMachine(vendingMachineStock);
        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("1")) {
                vml.displayItems();
                System.out.println("\nPress any key to return to Main Menu.");
                scanner.nextLine();
                ui.displayMainMenu();
            } else if (userInput.equals("2")) {        // Purchase Process
                ui.displayPurchasingMenu();
                while (true) {
                    String choice = scanner.nextLine();
                    if (choice.equals("1")) {   // Feed machine
                        System.out.println("How much money would you like to add to your balance (Please enter a whole dollar amount)?");
                        String valueToAdd = scanner.nextLine();
                        ui.feedMoney(valueToAdd);
                        ui.displayPurchasingMenu();
                    } else if (choice.equals("2")) {    // Select Product
                        System.out.println("Please select an item to purchase: ");
                        vml.displayItems();
                        while (true) {
                            String itemID = scanner.nextLine();
                            if (!vml.itemsForPurchase.containsKey(itemID)) {
                                System.out.println("Invalid selection.");
                                ui.displayPurchasingMenu();
                                break;
                            } else if (ui.getBalance().compareTo(vml.itemsForPurchase.get(itemID).getItemPrice()) == -1) {
                                System.out.println("Insufficient funds. Please select a different item or insert money.");
                                ui.displayPurchasingMenu();
                                break;
                            } else {
                                vml.itemsForPurchase.get(itemID).purchase();
                                vml.itemsForPurchase.get(itemID).printMessage();
                                ui.updateBalance(vml.itemsForPurchase.get(itemID).getItemPrice());
                                ui.displayPurchasingMenu();
                                break;
                            }
                        }
                    } else if (choice.equals("3")) {    // Finish Transaction
                        System.out.println(vml.dispenseChange(ui.getBalance()));
                        ui.updateBalance(ui.getBalance());
                        ui.displayMainMenu();
                        break;
                    }
                }
            } else if (userInput.equals("3")) {
                return;
            }
        }
    }

    public static void main(String[] args) {
        VendingMachineCLI cli = new VendingMachineCLI();
        cli.run();
    }


}
