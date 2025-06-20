package exeptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) {
        super(message);
    }

    private String productName;
    private int requestedQuantity;
    private int availableQuantity;

    public InsufficientStockException(String productName, int requestedQuantity, int availableQuantity) {
        super(String.format("Insufficient stock for %s. Requested: %d, Available: %d, Missing: %d",
                productName, requestedQuantity, availableQuantity,
                requestedQuantity - availableQuantity));
        this.productName = productName;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }

    // Getters
    public String getProductName() { return productName; }
    public int getRequestedQuantity() { return requestedQuantity; }
    public int getAvailableQuantity() { return availableQuantity; }
    public int getMissingQuantity() { return requestedQuantity - availableQuantity; }
}
