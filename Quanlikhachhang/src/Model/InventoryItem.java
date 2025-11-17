package Model;

public class InventoryItem {
    private int id;
    private String name;
    private double quantity;
    private String unit;
    private double minThreshold;

    public InventoryItem(int id, String name, double quantity, String unit, double minThreshold) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.minThreshold = minThreshold;
    }

    public boolean decrease(double amount) {
        if(amount <= quantity) {
            quantity -= amount;
            return true;
        } else {
            System.out.println("Không đủ " + name + " trong kho!");
            return false;
        }
    }

    public void increase(double amount) {
        quantity += amount;
    }

    public boolean needRestock() {
        return quantity < minThreshold;
    }

    public String getName() { return name; }
    public double getQuantity() { return quantity; }
}
