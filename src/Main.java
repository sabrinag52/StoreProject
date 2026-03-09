import exeptions.ExpiredProductException;
import exeptions.InsufficientStockException;
import model.*;
import service.StoreService;
import service.IStoreService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            Store store = new Store("Market");
            IStoreService service = new StoreService(store);

            setupStore(store, service);
            demonstrateSuccessfulSale(service);
            service.printStoreSummary();

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void setupStore(Store store, IStoreService service) {
        Category food = new Category("Food", 30.0);
        Category nonFood = new Category("Non-Food", 20.0);

        Product milk = new Product("Milk", 1.20, LocalDate.now().plusDays(4), food, 10) {
            @Override public double calculateSellingPrice() {
                return getDeliveryPrice() * (1 + getCategory().markup() / 100);
            }
        };

        Product soap = new Product("Soap", 2.50, LocalDate.now().plusMonths(6), nonFood, 5) {
            @Override public double calculateSellingPrice() {
                return getDeliveryPrice() * (1 + getCategory().markup() / 100);
            }
        };

        store.addProduct(milk);
        store.addProduct(soap);

        service.registerCashier(1, "Alice", 1200.0);
        store.addRegister(1);
        store.assignCashierToRegister(1, 1);  //  cashier 1 to register 1
    }

    private static void demonstrateSuccessfulSale(IStoreService service) {
        try {
            System.out.println("\n=== Attempting Successful Sale ===");

            Receipt receipt = service.createNewReceipt(1, 1);

            List<Product> products = service.getStore().getAvailableProducts();
            Product milk = products.stream()
                    .filter(p -> p.getName().equals("Milk"))
                    .findFirst()
                    .orElseThrow();

            receipt.addItem(milk, 2);

            // Process payment
            if (service.finalizeSale(receipt, 10.0)) {
                System.out.println("Sale successful!");
                System.out.println(receipt);
            } else {
                System.out.println("Payment failed");
            }

        } catch (Exception e) {
            System.err.println("Error during sale: " + e.getMessage());
        }
    }

}