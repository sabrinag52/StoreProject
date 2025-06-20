package model;
import java.time.LocalDate;

public class FoodProduct extends Product {
    public FoodProduct(String name, String bread, double deliveryPrice, LocalDate expiryDate) {
        super(name, deliveryPrice, expiryDate, new Category("Food", 30.0));
    }

    @Override
    public double calculateSellingPrice() {
        return deliveryPrice * (1 + category.markup() / 100);
    }
}