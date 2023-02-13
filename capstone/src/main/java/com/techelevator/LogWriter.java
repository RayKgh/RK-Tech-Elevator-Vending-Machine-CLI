package com.techelevator;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogWriter {
    final private File log = new File("Log.txt");
    final private LocalDateTime now = LocalDateTime.now();
    final private String dateTime = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));

    public void logTransaction(String transactionType, BigDecimal moneyInOrOut, BigDecimal newBalance) {
        try (final FileOutputStream vmLog = new FileOutputStream(log, true);
             final PrintWriter logWriter = new PrintWriter(vmLog)) {
            logWriter.printf("%s    %-18s $%s $%s\n", dateTime, transactionType, moneyInOrOut, newBalance);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void logTransaction(String itemName, String slotID, BigDecimal itemPrice, BigDecimal newBalance) {
        try (final FileOutputStream vmLog = new FileOutputStream(log, true);
             final PrintWriter logWriter = new PrintWriter(vmLog)) {
            logWriter.printf("%s %s %-18s $%s $%s\n", dateTime, slotID, itemName, itemPrice, newBalance);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

