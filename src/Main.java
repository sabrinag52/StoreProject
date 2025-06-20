import model.*;
import service.StoreService;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        // Step 1: Create Store + Service
        Store store = new Store("SuperJava Market");
        StoreService service = new StoreService(store);

        // Step 2: Create Categories
        Category food = new Category("Food", 30.0);
        Category nonFood = new Category("Non-Food", 20.0);

        // Step 3: Create Products
        Product milk = new Product("Milk", 1.20, LocalDate.now().plusDays(4), food) {
            @Override
            public double calculateSellingPrice() {
                return deliveryPrice * (1 + category.markup() / 100);
            }
        };

        Product soap = new Product("Soap", 2.50, LocalDate.now().plusMonths(6), nonFood) {
            @Override
            public double calculateSellingPrice() {
                return deliveryPrice * (1 + category.markup() / 100);
            }
        };

        store.addProduct(milk);
        store.addProduct(soap);

        // Step 4: Register a Cashier
        service.registerCashier(1, "Alice", 1200.0);

        // Step 5: Create Receipt
        Cashier cashier = store.getCashier(1);
        Receipt receipt = new Receipt(cashier);
        receipt.addItem(milk, 2);
        receipt.addItem(soap, 1);

        store.addReceipt(receipt);

        // Step 6: Output results
        System.out.println(receipt);
        service.printStoreSummary();
    }

}