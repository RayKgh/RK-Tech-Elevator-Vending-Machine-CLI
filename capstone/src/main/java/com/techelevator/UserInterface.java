package com.techelevator;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Scanner;

public class UserInterface {
    public BigDecimal getBalance() {
        return balance;
    }

    private BigDecimal balance = new BigDecimal("0.00");

//    Scanner

    public void displayMainMenu() {
        System.out.printf("Please select from the following menu options:\n\n" + "(1) Display Vending Machine Items\n" +
                "(2) Purchase\n" +
                "(3) Exit\n\n");
    }

    public void displayPurchasingMenu() {
        System.out.printf("Current Money Provided: $%s\n" +
                "(1) Feed Money\n" +
                "(2) Select Product\n" +
                "(3) Finish Transaction\n\n", getBalance());
    }

    public void feedMoney(String dollars) {
        BigDecimal valueToAdd = new BigDecimal(dollars);
        balance = balance.add(valueToAdd);

    }


}
