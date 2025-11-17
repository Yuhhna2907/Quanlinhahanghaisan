package Model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private Table table;
    private List<OrderItem> items;
    private String status; // Pending, Serving, Completed

    public Order(int id, Table table) {
        this.id = id;
        this.table = table;
        this.items = new ArrayList<>();
        this.status = "Pending";
    }

    public void addItem(MenuItem menuItem, int quantity) {
        items.add(new OrderItem(menuItem, quantity));
        System.out.println(quantity + " " + menuItem.getName() + " đã thêm vào order.");
    }

    public void removeItem(int index) {
        if(index >=0 && index < items.size()) {
            OrderItem removed = items.remove(index);
            System.out.println(removed.getMenuItem().getName() + " đã được xóa khỏi order.");
        }
    }

    public double calculateTotal() {
        double total = 0;
        for(OrderItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public void checkout() {
        double total = calculateTotal();
        System.out.println("Tổng hóa đơn: " + total + " VND");
        this.status = "Completed";
        table.free();
    }

    public String getStatus() { return status; }
    public Table getTable() { return table; }
    public List<OrderItem> getItems() { return items; }
}
