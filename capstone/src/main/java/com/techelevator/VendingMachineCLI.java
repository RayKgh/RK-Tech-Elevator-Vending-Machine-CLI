package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.util.Scanner;

public class VendingMachineCLI {
    Scanner scanner = new Scanner(System.in);

    public final UserInterface ui = new UserInterface();
    public final VendingMachine vm = new VendingMachine();

    public final LogWriter logWriter = new LogWriter();

    public void run() {
        // entry point for the vending machine
        ui.displayMainMenu();
        vm.StockVendingMachine();
        while (true) {
            String userInput = scanner.nextLine();
            if (userInput.equals("1")) {
                ui.showVendingMachineChoices(scanner, vm.vendingMachineItems);
            } else if (userInput.equals("2")) {        // Purchase Process
                ui.displayPurchasingMenu(vm.getBalance());
                    while (true) {
                        String choice = scanner.nextLine();
                        if (choice.equals("1")) {   // Feed machine
                            System.out.println("How much money would you like to add to your balance (Please enter a whole dollar amount)?");
                            String valueToAddToBalance = scanner.nextLine();
                            try {
                                if (Integer.parseInt(valueToAddToBalance) >= 0) {

                                }
                            } catch (NumberFormatException n) {
                                System.out.println("Please enter a valid whole dollar amount.");
                                ui.displayPurchasingMenu(vm.getBalance());
                                break;
                            }
                            vm.feedMoney(valueToAddToBalance);
                            logWriter.logTransaction("FEED MONEY:", new BigDecimal(valueToAddToBalance + ".00"), vm.getBalance());
                            ui.displayPurchasingMenu(vm.getBalance());
                        } else if (choice.equals("2")) {    // Select Product
                            System.out.println("Please select an item to purchase: ");
                            ui.displayItems(vm.vendingMachineItems);
                            while (true) {
                                String itemID = scanner.nextLine().toUpperCase();
                                if (!vm.itemsForPurchase.containsKey(itemID)) {
                                    System.out.println("Invalid selection.");
                                    ui.displayPurchasingMenu(vm.getBalance());
                                    break;
                                } else if (vm.getBalance().compareTo(vm.itemsForPurchase.get(itemID).getItemPrice()) == -1) {
                                    System.out.println("Insufficient funds. Please select a different item or insert money.");
                                    ui.displayPurchasingMenu(vm.getBalance());
                                    break;
                                } else {
                                    vm.itemsForPurchase.get(itemID).purchaseItem();
                                    vm.itemsForPurchase.get(itemID).printMessage();
                                    vm.updateBalance(vm.itemsForPurchase.get(itemID).getItemPrice());
                                    logWriter.logTransaction(vm.itemsForPurchase.get(itemID).getItemName(),
                                            vm.itemsForPurchase.get(itemID).getSlotID(),
                                            vm.itemsForPurchase.get(itemID).getItemPrice(), vm.getBalance());
                                    ui.displayPurchasingMenu(vm.getBalance());
                                    break;
                                }
                            }
                        } else if (choice.equals("3")) {    // Finish Transaction
                            System.out.println(vm.dispenseChange(vm.getBalance()));
                            BigDecimal changeToReturn = vm.getBalance();
                            vm.updateBalance(vm.getBalance());
                            logWriter.logTransaction("GIVE CHANGE:", changeToReturn, vm.getBalance());
                            ui.displayMainMenu();
                            break;
                        } else {
                            ui.invalidInputPrompt();
                            ui.displayPurchasingMenu(vm.getBalance());
                        }
                    }
            } else if (userInput.equals("3")) {
                return;
            } else if (userInput.equals("4")) {
                SalesReport salesReport = new SalesReport();
                salesReport.generateSalesReport(vm.vendingMachineItems);
                ui.displayMainMenu();
            } else {
                ui.invalidInputPrompt();
                ui.displayMainMenu();
            }
        }
    }

    public static void main(String[] args) {
        VendingMachineCLI cli = new VendingMachineCLI();
        cli.run();
    }





}
