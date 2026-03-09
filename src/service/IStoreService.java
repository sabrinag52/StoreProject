package service;

import exeptions.ExpiredProductException;
import exeptions.InsufficientStockException;
import model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IStoreService {
    void addProduct(Product product);
    void createAndAddProduct(String name, double price, LocalDate expiryDate, Category category, int quantity);

    void registerCashier(int id, String name, double salary);
    void assignCashierToRegister(int cashierId, int registerNumber);

    Receipt createReceipt(int cashierId, List<ReceiptItem> items)
            throws InsufficientStockException, ExpiredProductException;
    Receipt createNewReceipt(int cashierId, int registerNumber);
    boolean finalizeSale(Receipt receipt, double amountPaid);

    void printStoreSummary();
    BigDecimal calculateStoreProfit();

    // Getters
    Store getStore();
    List<Product> getAvailableProducts();
    List<Cashier> getCashiers();
}
