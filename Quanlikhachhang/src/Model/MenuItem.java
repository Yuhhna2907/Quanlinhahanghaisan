package Model;

public class MenuItem {
    private int id;
    private String name;
    private double price;
    private String unit;

    public MenuItem(int id, String name, double price, String unit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.unit = unit;
    }

    public double getPrice() { return price; }
    public String getName() { return name; }
}
