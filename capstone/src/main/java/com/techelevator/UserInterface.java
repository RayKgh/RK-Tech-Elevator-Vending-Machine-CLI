package com.techelevator;

import java.math.BigDecimal;

public class UserInterface {


    public void displayMainMenu() {
        System.out.printf("\n** MAIN MENU **\n" + "Please select from the following options:\n\n" + "(1) Display Vending Machine Items\n" +
                "(2) Purchase\n" +
                "(3) Exit\n\n");
    }

    public void displayPurchasingMenu(BigDecimal balance) {
        System.out.printf("\nCurrent Money Provided: $%s\n" +
                "(1) Feed Money\n" +
                "(2) Select Product\n" +
                "(3) Finish Transaction\n\n", balance);
    }






}
