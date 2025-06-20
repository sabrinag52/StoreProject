package exeptions;

import java.time.LocalDate;

public class ExpiredProductException extends RuntimeException {
    public ExpiredProductException(String message) {
        super(message);
    }

    private String productName;
    private LocalDate expirationDate;

    public ExpiredProductException(String productName, LocalDate expirationDate) {
        super(String.format("Product %s has expired on %s", productName, expirationDate));
        this.productName = productName;
        this.expirationDate = expirationDate;
    }

    public String getProductName() { return productName; }
    public LocalDate getExpirationDate() { return expirationDate; }
}
