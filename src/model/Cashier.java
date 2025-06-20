package model;
import java.util.Objects;

public class Cashier {

    private static int totalReceipts = 0;
    private static double totalRevenue = 0.0;

    private final int id;
    private String name;
    private double monthlySalary;
    private int currentRegisterNumber = -1; // -1 means unassigned
    private int issuedReceipts = 0;

    public Cashier(int id, String name, double monthlySalary) {
        if (id <= 0 || name == null || name.trim().isEmpty() || monthlySalary < 0) {
            throw new IllegalArgumentException("Invalid cashier data.");
        }
        this.id = id;
        this.name = name.trim();
        this.monthlySalary = monthlySalary;
    }


    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) throw new IllegalArgumentException("Name cannot be empty");
        this.name = name.trim();
    }

    public double getMonthlySalary() { return monthlySalary; }
    public void setMonthlySalary(double monthlySalary) {
        if (monthlySalary < 0) throw new IllegalArgumentException("Salary cannot be negative");
        this.monthlySalary = monthlySalary;
    }

    public int getCurrentRegisterNumber() { return currentRegisterNumber; }
    public boolean isAssignedToRegister() { return currentRegisterNumber > 0; }
    public void assignToRegister(int registerNumber) {
        if (registerNumber <= 0) throw new IllegalArgumentException("Register number must be positive");
        this.currentRegisterNumber = registerNumber;
    }
    public void unassignFromRegister() { this.currentRegisterNumber = -1; }

    public void incrementReceiptCount() {
        issuedReceipts++;
        int totalReceiptsIssued = 0;
        totalReceiptsIssued++;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Cashier other)) return false;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Cashier{ID=%d, Name='%s', Salary=%.2f, Register=%s, Receipts=%d}",
                id, name, monthlySalary,
                (currentRegisterNumber > 0 ? currentRegisterNumber : "Unassigned"),
                issuedReceipts);
    }

    public String getDisplayInfo() {
        return String.format("Cashier: %s (ID: %d) - Salary: %.2f BGN/month", name, id, monthlySalary);
    }
}


