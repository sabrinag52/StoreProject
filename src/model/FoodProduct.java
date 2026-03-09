package model;
import java.time.LocalDate;

public class FoodProduct extends Product {
    private static final double FOOD_MARKUP_PERCENT = 10.0;
    private static final int DISCOUNT_DAYS = 3;
    private static final double DISCOUNT_PERCENT = 15.0;

    public FoodProduct(String name, double deliveryPrice, LocalDate expiryDate, int quantity) {
        super(name, deliveryPrice, expiryDate, new Category("Food", FOOD_MARKUP_PERCENT), quantity);
    }

    @Override
    public double calculateSellingPrice() {
        double basePrice = getDeliveryPrice();
        return basePrice * (1 + FOOD_MARKUP_PERCENT / 100);
    }
    public double getDiscountedPrice() {
        return applyExpiryDiscount(DISCOUNT_DAYS, DISCOUNT_PERCENT);
    }

}