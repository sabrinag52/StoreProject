package service;

import exeptions.ExpiredProductException;
import exeptions.InsufficientStockException;
import model.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class StoreService implements IStoreService {
    private final Store store;

    public StoreService(Store store) {
        this.store = Objects.requireNonNull(store, "Store cannot be null");
    }

    @Override
    public void addProduct(Product product) {
        store.addProduct(product);
    }

    @Override
    public List<Product> getAvailableProducts() {
        return store.getAvailableProducts();
    }

    public List<Product> getAvailableProductsByCategory(Category category) {
        return store.getAvailableProducts().stream()
                .filter(p -> p.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    @Override
    public void createAndAddProduct(String name, double price, LocalDate expiryDate,
                                    Category category, int quantity) {
        Product product = new Product(name, price, expiryDate, category, quantity) {
            @Override
            public double calculateSellingPrice() {
                return price * (1 + category.markup() / 100);
            }
        };
        store.addProduct(product);
    }

    @Override
    public void registerCashier(int id, String name, double salary) {
        Cashier cashier = new Cashier(id, name, salary);
        store.addCashier(cashier);
    }

    @Override
    public void assignCashierToRegister(int cashierId, int registerNumber) {
        store.assignCashierToRegister(cashierId, registerNumber);
    }

    @Override
    public Receipt createReceipt(int cashierId, List<ReceiptItem> items)
            throws InsufficientStockException, ExpiredProductException {

        Cashier cashier = store.getCashier(cashierId)
                .orElseThrow(() -> new IllegalArgumentException("Cashier not found"));

        Receipt receipt = new Receipt(cashier);
        for (ReceiptItem item : items) {
            receipt.addItem(item.getProduct(), item.getQuantity());
        }
        return receipt;
    }

    @Override
    public Receipt createNewReceipt(int cashierId, int registerNumber) {
        Cashier cashier = store.getCashier(cashierId)
                .orElseThrow(() -> new IllegalArgumentException("Cashier not found"));

        Optional<Register> register = store.getRegister(registerNumber);
        if (register == null || !register.isEmpty()) {
            throw new IllegalStateException("Register not available");
        }
        return new Receipt(cashier);
    }

    @Override
    public boolean finalizeSale(Receipt receipt, double amountPaid) {
        if (receipt.processPayment(amountPaid)) {
            store.addReceipt(receipt);
            receipt.getCashier().incrementReceiptCount();
            try {
                receipt.saveToFile();
                return true;
            } catch (IOException e) {
                System.err.println("Failed to save receipt: " + e.getMessage());
            }
        }
        return false;
    }

    @Override
    public void printStoreSummary() {
        System.out.println("=== Store Summary ===");
        System.out.printf("Revenue: %.2f | Costs: %.2f | Profit: %.2f%n",
                store.getTotalRevenue(),
                store.getTotalCosts(),
                store.getProfit());
    }

    @Override
    public BigDecimal calculateStoreProfit() {
        return store.getProfit();
    }

    @Override
    public Store getStore() {
        return store;
    }

    @Override
    public List<Cashier> getCashiers() {
        return store.getCashiers();
    }
}