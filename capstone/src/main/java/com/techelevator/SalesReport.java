package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SalesReport {
    private BigDecimal totalSales = new BigDecimal("0.00");

    public void generateSalesReport(List<Item> vendingMachineItems) {
        System.out.println("Generating Sales Report");
        LocalDateTime now = LocalDateTime.now();
        final String dateTime = now.format(DateTimeFormatter.ofPattern("yyyy_MM_dd_HHmmss_"));
        final File sr = new File(dateTime + "salesreport.csv");
        try (final PrintWriter sRWriter = new PrintWriter(sr);) {
            BigDecimal totalSales = new BigDecimal("0.00");
            for (Item item : vendingMachineItems) {
                sRWriter.printf("%-18s | %d\n", item.getItemName(), item.getAmountSold());
                totalSales = totalSales.add(new BigDecimal(item.getAmountSold()).multiply(item.getItemPrice()));
            }
            sRWriter.println("**TOTAL SALES REVENUE** $" + totalSales);
            System.out.println("Done\n");
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
