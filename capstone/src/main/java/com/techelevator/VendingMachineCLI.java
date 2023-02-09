package com.techelevator;

public class VendingMachineCLI {

	final UserInterface ui = new UserInterface();

	public void run() {
		// entry point for the vending machine
		ui.displayMainMenu();

	}

	public static void main(String[] args) {
		VendingMachineCLI cli = new VendingMachineCLI();
		cli.run();
	}
}
