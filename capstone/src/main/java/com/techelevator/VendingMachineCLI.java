package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class VendingMachineCLI {
    Scanner scanner = new Scanner(System.in);

    public final UserInterface ui = new UserInterface();
    public final VendingMachine vm = new VendingMachine();
    public final File vendingMachineStock = new File("vendingmachine.csv");
    public final File machineLog = new File("vendingmachinelog.csv");

    public void run() {
        // entry point for the vending machine
        ui.displayMainMenu();
        vm.StockVendingMachine(vendingMachineStock);
        while (true) {
            String userInput = scanner.nextLine();

            if (userInput.equals("1")) {
                ui.displayItems(vm.vendingMachineItems);
                System.out.println("\nPress any key to return to Main Menu.");
                scanner.nextLine();
                ui.displayMainMenu();
            } else if (userInput.equals("2")) {        // Purchase Process
                ui.displayPurchasingMenu(vm.getBalance());
                try (final FileOutputStream log = new FileOutputStream(machineLog, true);
                     final PrintWriter logWriter = new PrintWriter(log)) {
                    while (true) {
                        String choice = scanner.nextLine();
                        if (choice.equals("1")) {   // Feed machine
                            System.out.println("How much money would you like to add to your balance (Please enter a whole dollar amount)?");
                            String valueToAdd = scanner.nextLine();
                            vm.feedMoney(valueToAdd);
                            logWriter.println(vm.printToLog("FEED MONEY:", new BigDecimal(valueToAdd + ".00")));
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
                                    logWriter.println(vm.printToLog(vm.itemsForPurchase.get(itemID).getItemName(),
                                            vm.itemsForPurchase.get(itemID).getSlotIdentifier(),
                                            vm.itemsForPurchase.get(itemID).getItemPrice()));
                                    ui.displayPurchasingMenu(vm.getBalance());
                                    break;
                                }
                            }
                        } else if (choice.equals("3")) {    // Finish Transaction
                            System.out.println(vm.dispenseChange(vm.getBalance()));
                            BigDecimal changeToReturn = vm.getBalance();
                            vm.updateBalance(vm.getBalance());
                            logWriter.println(vm.printToLog("GIVE CHANGE:", changeToReturn));
                            ui.displayMainMenu();
                            break;
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

            } else if (userInput.equals("3")) {
                return;
            } else if (userInput.equals("4")) {
                System.out.println("Generating Sales Report");
                LocalDateTime now = LocalDateTime.now();
                final String dateTime = now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss_"));
                final File salesReport = new File(dateTime + "salesreport.csv");
                vm.generateSalesReport(vm.vendingMachineItems, salesReport);
                System.out.println("Done\n");
                ui.displayMainMenu();
            }
        }
    }

    public static void main(String[] args) {
        VendingMachineCLI cli = new VendingMachineCLI();
        cli.run();
    }


}
