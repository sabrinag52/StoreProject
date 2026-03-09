package model;

public class Register {

    private final int registerNumber;
    private Cashier assignedCashier;

    public Register(int registerNumber) {
        if (registerNumber <= 0) {
            throw new IllegalArgumentException("Register number must be positive.");
        }
        this.registerNumber = registerNumber;
    }

    // Getters
    public int getRegisterNumber() { return registerNumber; }
    public Cashier getAssignedCashier() { return assignedCashier; }
    public boolean isOccupied() { return assignedCashier != null; }

    public void assignCashier(Cashier cashier) {
        if (cashier == null) {
            throw new IllegalArgumentException("Cashier cannot be null.");
        }
        if (isOccupied()) {
            throw new IllegalStateException("Register already occupied by: " + assignedCashier.getName());
        }
        this.assignedCashier = cashier;
        cashier.assignToRegister(registerNumber);
    }

    public void unassignCashier() {
        if (assignedCashier != null) {
            assignedCashier.unassignFromRegister();
            assignedCashier = null;
        }
    }

    @Override
    public String toString() {
        return String.format("Register #%d - %s",
                registerNumber,
                isOccupied() ? "Occupied by " + assignedCashier.getName() : "Available");
    }
}
