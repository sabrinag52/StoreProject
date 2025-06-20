package model;
import java.time.LocalDate;
import java.util.UUID;

public abstract class Product {
    protected final UUID id;
    protected final String name;
    protected final double deliveryPrice;
    protected final LocalDate expiryDate;
    protected final Category category;

    public Product(String name, double deliveryPrice, LocalDate expiryDate, Category category) {
        if (deliveryPrice <= 0) {
            throw new IllegalArgumentException("Delivery price must be positive.");
        }
        if (category == null) {
            throw new IllegalArgumentException("Product must have a category.");
        }

        this.id = UUID.randomUUID();
        this.name = name;
        this.deliveryPrice = deliveryPrice;
        this.expiryDate = expiryDate;
        this.category = category;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public double getDeliveryPrice() { return deliveryPrice; }
    public LocalDate getExpiryDate() { return expiryDate; }
    public Category getCategory() { return category; }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }

    public abstract double calculateSellingPrice();

    protected double applyExpiryDiscount(double discountDays, double discountPercent) {
        if (isExpired()) return 0.0;
        long daysUntilExpiry = LocalDate.now().until(expiryDate).getDays();
        double originalPrice = calculateSellingPrice();

        if (daysUntilExpiry <= discountDays) {
            return originalPrice * (1 - discountPercent / 100);
        }
        return originalPrice;
    }

    @Override
    public String toString() {
        return String.format(
                "Product[id=%s, name=%s, price=%.2f, category=%s, expiry=%s]",
                id, name, deliveryPrice, category.name(), expiryDate
        );
    }

}
