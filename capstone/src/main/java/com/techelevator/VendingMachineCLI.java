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
