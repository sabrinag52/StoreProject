//package test;
//
//import model.*;
//
//import java.time.LocalDate;
//import java.io.IOException;
//
//import exeptions.*;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ReceiptTest {
//    private Receipt receipt;
//    private Cashier cashier;
//    private Product validProduct;
//    private Product expiredProduct;
//
//    @BeforeEach
//    void setUp() {
//        cashier = new Cashier(101, "Test Cashier", 1200.0);
//        receipt = new Receipt(cashier);
//
//        Category food = new Category("Food", 10.0);
//        validProduct = new Product("Bread", 1.20,
//                LocalDate.now().plusDays(10),
//                food, 5) {
//            @Override
//            public double calculateSellingPrice() { return 1.32; }
//        };
//
//        expiredProduct = new Product("Expired Milk", 1.0,
//                LocalDate.now().minusDays(1),
//                food, 1) {
//            @Override
//            public double calculateSellingPrice() { return 1.0; }
//        };
//    }
//
//    @Test
//    void addItem_ValidProduct_AddsToReceipt() throws ExpiredProductException, InsufficientStockException {
//        receipt.addItem(validProduct, 2);
//        assertEquals(1, receipt.getItems().size());
//        assertEquals(2, receipt.getItems().get(0).getQuantity());
//    }
//
//    @Test
//    void addItem_ExpiredProduct_ThrowsException() {
//        assertThrows(ExpiredProductException.class,
//                () -> receipt.addItem(expiredProduct, 1));
//    }
//
//    @Test
//    void addItem_InsufficientStock_ThrowsException() {
//        assertThrows(InsufficientStockException.class,
//                () -> receipt.addItem(validProduct, 10));
//    }
//
//    @Test
//    void calculateTotal_WithItems_ReturnsCorrectSum() throws ExpiredProductException, InsufficientStockException {
//        receipt.addItem(validProduct, 2);
//        assertEquals(2.64, receipt.calculateTotal(), 0.001);
//    }
//
//    @Test
//    void processPayment_SufficientAmount_ReturnsTrue() throws ExpiredProductException, InsufficientStockException {
//        receipt.addItem(validProduct, 1);
//        assertTrue(receipt.processPayment(2.0));
//    }
//
//    @Test
//    void processPayment_InsufficientAmount_ReturnsFalse() throws ExpiredProductException, InsufficientStockException {
//        receipt.addItem(validProduct, 1);
//        assertFalse(receipt.processPayment(1.0));
//    }
//}
