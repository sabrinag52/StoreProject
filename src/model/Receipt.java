package model;

import exeptions.ExpiredProductException;
import exeptions.InsufficientStockException;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class Receipt implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private static int totalReceipts = 0;
    private static double totalRevenue = 0.0;
    private static final Object revenueLock = new Object();

    private final int id;
    private final Cashier cashier;
    private final LocalDateTime dateTime;
    private final List<ReceiptItem> items;
    private boolean isPaid;

    public Receipt(Cashier cashier) {
        synchronized (revenueLock) {
            this.id = ++totalReceipts;
        }

        this.cashier = cashier;
        this.dateTime = LocalDateTime.now();
        this.items = new ArrayList<>();
        this.isPaid = false;
    }

    public void addItem(Product product, double quantity) throws ExpiredProductException, InsufficientStockException {
        if (product.isExpired()) {
            throw new ExpiredProductException(product.getName(), product.getExpiryDate());
        }
        product.reduceQuantity(quantity);
        double price = product.calculateSellingPrice();
        items.add(new ReceiptItem(product, (int) quantity, price));
    }

    public double calculateTotal() {
        double total = items.stream().mapToDouble(ReceiptItem::getTotalPrice).sum();
        totalRevenue += total;
        return total;
    }

    public boolean processPayment(double amountPaid) {
        double total = calculateTotal();
        if (amountPaid >= total) {
            synchronized (revenueLock) {
                totalRevenue += total;
            }
            this.isPaid = true;
            cashier.incrementReceiptCount();
            return true;
        }
        return false;
    }

    public void saveToFile() throws IOException {
        File receiptsDir = new File("receipts");
        if (!receiptsDir.exists()) {
            receiptsDir.mkdir();
        }
        String fileName = "receipts/receipt_" + id + ".ser";
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(this);
        }
    }

    public static Receipt readFromFile(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Receipt) in.readObject();
        }
    }

    // Getters
    public int getId() {return id;}
    public Cashier getCashier() {return cashier;}
    public LocalDateTime getDateTime() {return dateTime;}
    public List<ReceiptItem> getItems() {return new ArrayList<>(items);}
    public boolean isPaid() {return isPaid;}
    public static int getTotalReceipts() {return totalReceipts;}
    public static double getTotalRevenue() {return totalRevenue;}

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
        return String.format("=== Receipt #%d (%s) ===\nTotal: %.2f\nPaid: %s",
                id,
                dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                calculateTotal(),
                isPaid ? "YES" : "NO");
    }
}