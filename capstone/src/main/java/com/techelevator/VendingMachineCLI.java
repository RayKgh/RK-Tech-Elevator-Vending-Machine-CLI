package com.techelevator;

import java.math.BigDecimal;
import java.util.Scanner;

public class VendingMachineCLI {
    Scanner scanner = new Scanner(System.in);

    public final UserInterface ui = new UserInterface();
    public final VendingMachine vm = new VendingMachine();

    public final LogWriter logWriter = new LogWriter();

    public void run() {
        // entry point for the vending machine
        vm.StockVendingMachine();
        while (true) {
            ui.displayMainMenu();
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
                    break;
                default:
                    ui.invalidInputPrompt();
                    break;
            }
        }
    }

    public static void main(String[] args) {
        VendingMachineCLI cli = new VendingMachineCLI();
        cli.run();
    }

    public void purchaseProcess() {
        label:
        while (true) { // replace true with boolean var
            ui.displayPurchasingMenu(vm.getBalance());
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    feedMachine();
                    break;
                case "2":
                    selectProduct();
                    break;
                case "3":
                    finishTransaction();
                    break label;
                default:
                    ui.invalidInputPrompt();
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
        int valueToAddToBalance = 0;
        try {
            valueToAddToBalance = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a whole dollar amount.");
        }
        if (valueToAddToBalance <= 0) {
            ui.displayMessage("Cannot insert negative dollars.");
        } else {
            BigDecimal dollarAmount = new BigDecimal(valueToAddToBalance);
            vm.feedMoney(dollarAmount);
            logWriter.logTransaction("FEED MONEY:", new BigDecimal(valueToAddToBalance + ".00"), vm.getBalance());
        }
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
            vm.updateBalance(vm.itemsForPurchase.get(itemID).getItemPrice());
            logWriter.logTransaction(vm.itemsForPurchase.get(itemID).getItemName(),
                    vm.itemsForPurchase.get(itemID).getSlotID(),
                    vm.itemsForPurchase.get(itemID).getItemPrice(), vm.getBalance());
            ui.displayPurchasingMenu(vm.getBalance());
        }
    }

    public void finishTransaction() {
        BigDecimal changeToReturn = vm.getBalance();
        System.out.println(vm.dispenseChange(vm.getBalance()));
        logWriter.logTransaction("GIVE CHANGE:", changeToReturn, vm.getBalance());
        ui.displayMainMenu();
    }


}
