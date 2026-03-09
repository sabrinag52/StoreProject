//package test;
//import model.*;
//import exeptions.*;
//import org.junit.jupiter.api.Test;
//import java.time.LocalDate;
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ProductTest {
//    private Product testProduct;
//    private Category foodCategory;
//
//    @BeforeEach
//    void setUp() {
//        foodCategory = new Category("Food", 30.0);
//        testProduct = new Product("Test Product", 1.0,
//                LocalDate.now().plusDays(10),
//                foodCategory, 5) {
//            @Override
//            public double calculateSellingPrice() {
//                return getDeliveryPrice() * (1 + getCategory().markup() / 100);
//            }
//        };
//    }
//
//    @Test
//    void reduceQuantity_ValidAmount_ReducesQuantity() throws InsufficientStockException {
//        testProduct.reduceQuantity(3);
//        assertEquals(2, testProduct.getQuantity());
//    }
//
//    @Test
//    void reduceQuantity_ExceedsAvailable_ThrowsException() {
//        assertThrows(InsufficientStockException.class,
//                () -> testProduct.reduceQuantity(6));
//    }
//
//    @Test
//    void isExpired_WhenNotExpired_ReturnsFalse() {
//        assertFalse(testProduct.isExpired());
//    }
//
//    @Test
//    void isExpired_WhenExpired_ReturnsTrue() {
//        Product expiredProduct = new Product("Expired", 1.0,
//                LocalDate.now().minusDays(1),
//                foodCategory, 1) {
//            @Override
//            public double calculateSellingPrice() { return 1.0; }
//        };
//        assertTrue(expiredProduct.isExpired());
//    }
//}
