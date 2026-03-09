package model;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class Store {
    private final String name;
    private final Map<String, Product> products;
    private final Map<Integer, Cashier> cashiers;
    private final List<Receipt> receipts;
    private final Map<Category, List<Product>> categorizedProducts;
    private final Map<Integer, Register> registers;

    private double foodMarkupPercent = 10.0;
    private double nonFoodMarkupPercent = 5.0;
    private int expiryThresholdDays = 3;
    private double expiryDiscountPercent = 15.0;

    public Store(String name) {
        this.name = name;
        this.registers = new HashMap<>();
        this.products = new HashMap<>();
        this.cashiers = new HashMap<>();
        this.receipts = new ArrayList<>();
        this.categorizedProducts = new HashMap<>();
    }


    public void addCashier(Cashier cashier) {
        Objects.requireNonNull(cashier, "Cashier cannot be null");
        if (cashiers.containsKey(cashier.getId())) {
            throw new IllegalArgumentException("Cashier ID already exists");
        }
        cashiers.put(cashier.getId(), cashier);
    }

    public Optional<Cashier> getCashier(int id) {
        return Optional.ofNullable(cashiers.get(id));
    }

    public void addReceipt(Receipt receipt) {
        receipts.add(Objects.requireNonNull(receipt));
    }

    public List<Receipt> getReceipts() {
        return Collections.unmodifiableList(receipts);
    }


    public void addRegister(int registerNumber) {
        if (registerNumber <= 0) {
            throw new IllegalArgumentException("Register number must be positive");
        }
        registers.putIfAbsent(registerNumber, new Register(registerNumber));
    }

    public void assignCashierToRegister(int cashierId, int registerNumber) {
        Cashier cashier = getCashier(cashierId)
                .orElseThrow(() -> new IllegalArgumentException("Cashier not found"));

        Register register = registers.get(registerNumber);
        if (register == null) {
            throw new IllegalArgumentException("Register not found");
        }

        register.assignCashier(cashier);
    }

    public BigDecimal getTotalRevenue() {
        return receipts.stream()
                .map(r -> BigDecimal.valueOf(r.calculateTotal()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getProfit() {
        return getTotalRevenue()
                .subtract(getTotalDeliveryCost())
                .subtract(getTotalSalaries());
    }

    public void setFoodMarkupPercent(double percent) {
        validatePercentage(percent);
        this.foodMarkupPercent = percent;
    }

    public void setExpiryDiscountPercent(double percent) {
        validatePercentage(percent);
        this.expiryDiscountPercent = percent;
    }

    private void validatePercentage(double percent) {
        if (percent < 0 || percent > 100) {
            throw new IllegalArgumentException("Percentage must be between 0-100");
        }
    }

    public void addProduct(Product product) {
        Objects.requireNonNull(product, "Product cannot be null");
        UUID productId = product.getId();

//        products.put(productId, product);
        categorizedProducts
                .computeIfAbsent(product.getCategory(), k -> new ArrayList<>())
                .add(product);
    }

    public List<Product> getAvailableProducts() {
        return products.values().stream()
                .filter(product -> !product.isExpired())
                .filter(product -> product.getQuantity() > 0)
                .collect(Collectors.toList());
    }

    public List<Cashier> getCashiers() {
        return new ArrayList<>(cashiers.values());
    }

    public BigDecimal getTotalCosts() {
        return getTotalDeliveryCost().add(getTotalSalaries());
    }

    public BigDecimal getTotalDeliveryCost() {
        return products.values().stream()
                .map(p -> BigDecimal.valueOf(p.getDeliveryPrice()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalSalaries() {
        return cashiers.values().stream()
                .map(c -> BigDecimal.valueOf(c.getMonthlySalary()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Optional<Register> getRegister(int registerNumber) {
        return Optional.ofNullable(registers.get(registerNumber));
    }

    @Override
    public String toString() {
        return String.format(
                "Store[name=%s, products=%d, cashiers=%d, receipts=%d, registers=%d]",
                name, products.size(), cashiers.size(), receipts.size(), registers.size()
        );
    }
}
