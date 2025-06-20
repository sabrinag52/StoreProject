package test;

import model.*;

import java.time.LocalDate;
import java.io.IOException;

public class ReceiptTest {
    public static void main(String[] args) {
        // Use static sample data instead of dynamic (LocalDate.now)

        // Static cashier
        Cashier cashier = new Cashier(101, "TestCashier", 1200.00);

        // Static products with fixed expiration dates
        Product bread = new FoodProduct("F001", "Bread", 1.20, LocalDate.of(2025, 6, 30));
        Product detergent = new NonFoodProduct("N001", "Detergent", 3.50, LocalDate.of(2026, 1, 1));

        // Create receipt with static items
        Receipt receipt = new Receipt(cashier);
        receipt.addItem(bread, 2);
        receipt.addItem(detergent, 1);

        // Print the receipt
        System.out.println("--- STATIC RECEIPT TEST ---");
        System.out.println(receipt);

        // Save the receipt
        try {
            receipt.saveToFile();
            System.out.println("Receipt file saved.");
        } catch (IOException e) {
            System.err.println("Error saving receipt: " + e.getMessage());
        }

        // Load and verify the receipt
        try {
            Receipt loaded = Receipt.readFromFile("receipt_" + receipt.getId() + ".txt");
            System.out.println("Loaded from file:");
            System.out.println(loaded);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error reading saved receipt: " + e.getMessage());
        }
    }
}
