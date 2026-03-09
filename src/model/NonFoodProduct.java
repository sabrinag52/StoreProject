package model;
import java.time.LocalDate;

public class NonFoodProduct extends Product {
    private static final double NON_FOOD_MARKUP_PERCENT = 5.0;

    public NonFoodProduct(String name, double deliveryPrice, LocalDate expiryDate, int quantity) {
        super(name, deliveryPrice, expiryDate, new Category("Non-Food", NON_FOOD_MARKUP_PERCENT), quantity);
    }

    @Override
    public double calculateSellingPrice() {
        double basePrice = getDeliveryPrice();
        return basePrice * (1 + NON_FOOD_MARKUP_PERCENT / 100);
    }
}