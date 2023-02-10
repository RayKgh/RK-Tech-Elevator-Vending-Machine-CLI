package com.techelevator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class UserInterface {
    public BigDecimal getBalance() {
        return balance;
    }

    private BigDecimal balance = new BigDecimal("0.00");


    public void displayMainMenu() {
        System.out.printf("\n** MAIN MENU **\n" + "Please select from the following options:\n\n" + "(1) Display Vending Machine Items\n" +
                "(2) Purchase\n" +
                "(3) Exit\n\n");
    }

    public void displayPurchasingMenu() {
        System.out.printf("\nCurrent Money Provided: $%s\n" +
                "(1) Feed Money\n" +
                "(2) Select Product\n" +
                "(3) Finish Transaction\n\n", getBalance());
    }

    public void feedMoney(String dollars) {
        BigDecimal valueToAdd = new BigDecimal(dollars);
        balance = balance.add(valueToAdd);

    }

    public void updateBalance(BigDecimal itemPrice) {
        balance = balance.subtract(itemPrice);
    }


}
