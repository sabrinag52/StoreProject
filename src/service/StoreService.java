package service;

import model.*;

import java.time.LocalDate;
import java.util.List;

public class StoreService {
    private final Store store;

    public StoreService(Store store) {
        this.store = store;
    }

    // Add a product with custom attributes
    public void createAndAddProduct(String name, double price, LocalDate expiryDate, Category category) {
        Product product = new Product(name, price, expiryDate, category) {
            @Override
            public double calculateSellingPrice() {
                return price * (1 + category.markup() / 100);
            }
        };
        store.addProduct(product);
    }

    // Create a new cashier and add to the store
    public void registerCashier(int id, String name, double salary) {
        Cashier cashier = new Cashier(id, name, salary);
        store.addCashier(cashier);
    }

    // Create and process a receipt
    public Receipt createReceipt(int cashierId, List<ReceiptItem> items) {
        Cashier cashier = store.getCashier(cashierId);
        if (cashier == null) throw new IllegalArgumentException("Cashier not found.");

        Receipt receipt = new Receipt(cashier);
        for (ReceiptItem item : items) {
            receipt.addItem(item.getProduct(), item.getQuantity());
        }

        store.addReceipt(receipt);
        cashier.incrementReceiptCount();

        return receipt;
    }

    // Generate summary report
    public void printStoreSummary() {
        System.out.println("Store Summary");
        System.out.println("------------------------------");
        System.out.println("Total Revenue: " + store.getTotalRevenue());
        System.out.println("Total Delivery Cost: " + store.getTotalDeliveryCost());
        System.out.println("Total Salaries: " + store.getTotalSalaries());
        System.out.println("Profit: " + store.getProfit());
        System.out.println("------------------------------");
    }

    public Store getStore() {
        return store;
    }
}