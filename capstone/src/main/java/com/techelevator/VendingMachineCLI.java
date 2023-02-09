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
			} else if (userInput.equals("2")) {
				ui.displayPurchasingMenu();
				while (true) {
					String choice = scanner.nextLine();
					if (choice.equals("1")) {
						System.out.println("How much money would you like to add to your balance (Please enter a whole dollar amount)?");
						String valueToAdd = scanner.nextLine();
						ui.feedMoney(valueToAdd);
						ui.displayPurchasingMenu();
						// Feed money
					} else if (choice.equals("2")) {
						// Select Product
					}  else if (choice.equals("3")) {
						break;
						// ui.displayMainMenu();
					}
				}
				ui.displayMainMenu();
			} if (userInput.equals("3")) {
				return;
			}
		}

	}

	public static void main(String[] args) {
		VendingMachineCLI cli = new VendingMachineCLI();
		cli.run();
	}


}
