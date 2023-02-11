package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

public class SalesReportWriter {
    private BigDecimal totalSales = new BigDecimal("0.00");

    public void generateSalesReport(List<Item> vendingMachineItems, File salesReport) {
        try (final PrintWriter sRWriter = new PrintWriter(salesReport);) {
            BigDecimal totalSales = new BigDecimal("0.00");
            for (Item item : vendingMachineItems) {
                sRWriter.printf("%-18s | %d\n", item.getItemName(), item.getAmountSold());
                totalSales = totalSales.add(new BigDecimal(item.getAmountSold()).multiply(item.getItemPrice()));
            }
            sRWriter.println("**TOTAL SALES REVENUE** $" + totalSales);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

}
