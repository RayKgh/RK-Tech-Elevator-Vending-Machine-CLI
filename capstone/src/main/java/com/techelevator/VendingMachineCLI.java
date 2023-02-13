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
            switch (userInput) {
                case "1":
                    ui.showVendingMachineChoices(scanner, vm.vendingMachineItems);
                    break;
                case "2":
                    purchaseProcess();
                    break;
                case "3":   // Exits program
                    return;
                case "4":   // Hidden menu option
                    generateSalesReport();
                    ui.displayMainMenu();
                    break;
                default:
                    ui.invalidInputPrompt();
                    ui.displayMainMenu();
                    break;
            }
        }
    }

    public static void main(String[] args) {
        VendingMachineCLI cli = new VendingMachineCLI();
        cli.run();
    }

    public void purchaseProcess() {
        ui.displayPurchasingMenu(vm.getBalance());
        label:
        while (true) {
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    feedMachine();
                    ui.displayPurchasingMenu(vm.getBalance());
                    break;
                case "2":
                    selectProduct();
                    break;
                case "3":
                    finishTransaction();
                    break label;
                default:
                    ui.invalidInputPrompt();
                    ui.displayPurchasingMenu(vm.getBalance());
                    break;
            }
        }
    }

    public void generateSalesReport() {
        SalesReport salesReport = new SalesReport();
        salesReport.generateSalesReport(vm.vendingMachineItems);
    }

    public void feedMachine() {
        ui.displayMessage("How much money would you like to add to your balance (Please enter a whole dollar amount)?");
        String valueToAddToBalance = scanner.nextLine();
        // need to validate that userInput can be parsed to integer
        vm.feedMoney(valueToAddToBalance);
        logWriter.logTransaction("FEED MONEY:", new BigDecimal(valueToAddToBalance + ".00"), vm.getBalance());
    }

    public void selectProduct(){
        ui.displayMessage("Please select an item to purchase: ");
        ui.displayItems(vm.vendingMachineItems);
        String itemID = scanner.nextLine().toUpperCase();
        if (!vm.itemsForPurchase.containsKey(itemID)) {
            ui.displayMessage("Invalid selection.");
            ui.displayPurchasingMenu(vm.getBalance());
        } else if (vm.getBalance().compareTo(vm.itemsForPurchase.get(itemID).getItemPrice()) == -1) {
            ui.displayMessage("Insufficient funds. Please select a different item or insert money.");
            ui.displayPurchasingMenu(vm.getBalance());
        } else {
            vm.itemsForPurchase.get(itemID).purchaseItem();
            vm.itemsForPurchase.get(itemID).printMessage();
            vm.updateBalance(vm.itemsForPurchase.get(itemID).getItemPrice());
            logWriter.logTransaction(vm.itemsForPurchase.get(itemID).getItemName(),
                    vm.itemsForPurchase.get(itemID).getSlotID(),
                    vm.itemsForPurchase.get(itemID).getItemPrice(), vm.getBalance());
            ui.displayPurchasingMenu(vm.getBalance());
        }
    }

    public void finishTransaction() {
        System.out.println(vm.dispenseChange(vm.getBalance()));
        BigDecimal changeToReturn = vm.getBalance();
        vm.updateBalance(vm.getBalance()); // updates balance to zero by subtracting the balance
        logWriter.logTransaction("GIVE CHANGE:", changeToReturn, vm.getBalance());
        ui.displayMainMenu();
    }


}
