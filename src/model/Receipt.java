package model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Receipt implements Serializable{
    @Serial
    private static final long serialVersionUID = 1L;

    private static int totalReceipts = 0;
    private static double totalRevenue = 0.0;

    private final int id;
    private final Cashier cashier;
    private final LocalDateTime dateTime;
    private final List<ReceiptItem> items;

    public Receipt(Cashier cashier) {
        this.id = ++totalReceipts;
        this.cashier = cashier;
        this.dateTime = LocalDateTime.now();
        this.items = new ArrayList<>();
    }

    // Add product to receipt
    public void addItem(Product product, int quantity) {
        if (product.isExpired()) {
            throw new IllegalArgumentException("expired product can't be selled: " + product.getName());
        }
        double price = product.calculateSellingPrice();
        items.add(new ReceiptItem(product, quantity, price));
    }

    // Calculate total and update revenue
    public double calculateTotal() {
        double total = items.stream().mapToDouble(ReceiptItem::getTotalPrice).sum();
        totalRevenue += total;
        return total;
    }

    // Save receipt to file using serialization
    public void saveToFile() throws IOException {
        String fileName = "receipt_" + id + ".txt";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
        }
    }

    // Read receipt from file
    public static Receipt readFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Receipt) in.readObject();
        }
    }

    // Getters
    public int getId() { return id; }
    public Cashier getCashier() { return cashier; }
    public LocalDateTime getDateTime() { return dateTime; }
    public List<ReceiptItem> getItems() { return new ArrayList<>(items); }

    // Static stats
    public static int getTotalReceipts() { return totalReceipts; }
    public static double getTotalRevenue() { return totalRevenue; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Receipt #").append(id).append(" ===\n");
        sb.append("Cashier: ").append(cashier.getName()).append("\n");
        sb.append("Date: ").append(dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append("\n");
        sb.append("Items:\n");
        for (ReceiptItem item : items) {
            sb.append("  ").append(item).append("\n");
        }
        sb.append("Total: ").append(String.format("%.2f", calculateTotal())).append("\n");
        return sb.toString();
    }
}