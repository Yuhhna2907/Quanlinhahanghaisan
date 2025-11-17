package Service;

import Model.Order;
import Model.Table;
import Model.MenuItem;

import java.util.List;

public class OrderService {
    private List<Order> orders;

    public OrderService(List<Order> orders) {
        this.orders = orders;
    }

    public Order createOrder(Table table) {
        Order order = new Order(orders.size() + 1, table);
        orders.add(order);
        table.occupy();
        return order;
    }

    public void addItem(Order order, MenuItem item, int qty) {
        order.addItem(item, qty);
    }

    public void removeItem(Order order, int index) {
        order.removeItem(index);
    }

    public void moveOrder(Order order, Table newTable) {
        Table oldTable = order.getTable();
        if (newTable.getStatus().equals("Available")) {
            oldTable.free();
            newTable.occupy();
            System.out.println("Chuyển order từ bàn " + oldTable.getId() + " sang bàn " + newTable.getId());
        } else {
            System.out.println("Bàn mới không khả dụng!");
        }
    }
}
