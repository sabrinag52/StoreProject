package model;

import java.util.*;

public class Store {
    private final String name;
    private final Map<String, Product> products;
    private final Map<Integer, Cashier> cashiers;
    private final List<Receipt> receipts;
    private final Map<Category, List<Product>> categorizedProducts;

    private double foodMarkupPercent = 30.0;
    private double nonFoodMarkupPercent = 20.0;
    private int expiryThresholdDays = 3;
    private double expiryDiscountPercent = 15.0;

    public Store(String name) {
        this.name = name;
        this.products = new HashMap<>();
        this.cashiers = new HashMap<>();
        this.receipts = new ArrayList<>();
        this.categorizedProducts = new HashMap<>();
    }

    // Register a new product
    public void addProduct(Product product) {
        products.put(product.getId().toString(), product);
        categorizedProducts
                .computeIfAbsent(product.getCategory(), k -> new ArrayList<>())
                .add(product);
    }

    // Get a product by ID
    public Product getProduct(String id) {return products.get(id);}

    // Register a new cashier
    public void addCashier(Cashier cashier) {
        cashiers.put(cashier.getId(), cashier);
    }

    // Get a cashier by ID
    public Cashier getCashier(int id) {
        return cashiers.get(id);}

    // Add a receipt
    public void addReceipt(Receipt receipt) {
        receipts.add(receipt);
    }
    public List<Receipt> getReceipts() {return receipts;}
    public double getTotalRevenue() {return receipts.stream().mapToDouble(Receipt::calculateTotal).sum();}
    public double getTotalDeliveryCost() {return products.values().stream().mapToDouble(Product::getDeliveryPrice).sum();}
    public double getTotalSalaries() {return cashiers.values().stream().mapToDouble(Cashier::getMonthlySalary).sum();}
    public double getProfit() {return getTotalRevenue() - getTotalDeliveryCost() - getTotalSalaries();}

    // Configurable settings
    public void setFoodMarkupPercent(double percent) {this.foodMarkupPercent = percent;}
    public void setNonFoodMarkupPercent(double percent) {this.nonFoodMarkupPercent = percent;}
    public void setExpiryThresholdDays(int days) {this.expiryThresholdDays = days;}
    public void setExpiryDiscountPercent(double percent) {this.expiryDiscountPercent = percent;}

    public double getFoodMarkupPercent() {return foodMarkupPercent;}
    public double getNonFoodMarkupPercent() {return nonFoodMarkupPercent;}
    public int getExpiryThresholdDays() {return expiryThresholdDays;}
    public double getExpiryDiscountPercent() {return expiryDiscountPercent;}
    public Map<Category, List<Product>> getCategorizedProducts() {return categorizedProducts;}

    @Override
    public String toString() {
        return String.format("Store[name=%s, products=%d, cashiers=%d, receipts=%d]",
                name, products.size(), cashiers.size(), receipts.size());
    }
}
