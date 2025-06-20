package model;
import java.time.LocalDate;

public class NonFoodProduct extends Product {
    public NonFoodProduct(String name, String detergent, double deliveryPrice, LocalDate expiryDate) {
        super(name, deliveryPrice, expiryDate, new Category("Non-Food", 20.0));
    }

    @Override
    public double calculateSellingPrice() {
        return deliveryPrice * (1 + category.markup() / 100);
    }
}