package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogWriter {
    final private File log = new File("Log.txt");
    final private LocalDateTime now = LocalDateTime.now();
    final private String dateTime = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

    /**
     * This logTransaction() method is used to log when the customer adds a value to their balance or when change is
     * given at the end of a transaction.
     * @param transactionType e.g. FEED MONEY, GIVE CHANGE
     * @param moneyInOrOut Money added or change given
     * @param newBalance User balance at the end of the transaction
     */
    public void logTransaction(String transactionType, BigDecimal moneyInOrOut, BigDecimal newBalance) {
        try (final FileOutputStream vmLog = new FileOutputStream(log, true);
             final PrintWriter logWriter = new PrintWriter(vmLog)) {
            logWriter.printf("%s    %-18s $%s $%s\n", dateTime, transactionType, moneyInOrOut, newBalance);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This logTransaction() method is used to log purchases.
     * @param itemName Name of item
     * @param slotID Item's slot location in the vending machine
     * @param itemPrice Item price
     * @param newBalance Balance at the end of the transaction
     */
    public void logTransaction(String itemName, String slotID, BigDecimal itemPrice, BigDecimal newBalance) {
        try (final FileOutputStream vmLog = new FileOutputStream(log, true);
             final PrintWriter logWriter = new PrintWriter(vmLog)) {
            logWriter.printf("%s %s %-18s $%s $%s\n", dateTime, slotID, itemName, itemPrice, newBalance);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

