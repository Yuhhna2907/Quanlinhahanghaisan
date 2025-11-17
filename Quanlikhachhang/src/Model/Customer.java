package Model;

import java.awt.*;

public class Customer {
    private int id;
    private String name;
    private String phone;

    public Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public void reserveTable(Table table) {
        if(table.getStatus().equals("Available")) {
            table.reserve();
            System.out.println(name + " đã đặt bàn " + table.getId());
        } else {
            System.out.println("Bàn không khả dụng");
        }
    }

    public void orderFood(Order order, MenuItem menuItem, int quantity) {
        order.addItem(menuItem, quantity);
    }

    public void requestPayment(Order order) {
        order.checkout();
    }

    // Getter & Setter
    public int getId() { return id; }
    public String getName() { return name; }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

