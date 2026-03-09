package model;

import exeptions.ExpiredProductException;
import exeptions.InsufficientStockException;

import  java.io.Serializable;

public class ReceiptItem  implements Serializable{
    private static final long serialVersionUID = 1L;

    private final Product product;
    private final int quantity;
    private final double unitPrice;

    public ReceiptItem(Product product, int quantity, double unitPrice) {
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (unitPrice < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }

        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public Product getProduct() {return product;}
    public int getQuantity() {return quantity;}
    public double getUnitPrice() {return unitPrice;}
    public double getTotalPrice() {return quantity * unitPrice;}

    @Override
    public String toString() {
        return String.format("%s x%d @ %.2f = %.2f", product.getName(), quantity, unitPrice, getTotalPrice());
    }
}
