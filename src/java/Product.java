package java;

public class Product {
    protected String id;
    protected String name;
    protected double deliveryPrice;
    protected String category; // "food" or "non-food"
    protected LocalDate expirationDate;

    public abstract double getSellingPrice();

    // Getters, setters, toString...
}
