package Main;

import Model.Order;
import Model.Table;
import Service.OrderService;

import java.util.ArrayList;
import java.util.List;
import Model.MenuItem;

public class MainOrder {
    public static void main(String[] args) {
        List<Order> orders = new ArrayList<>();
        OrderService orderService = new OrderService(orders);

        Table t1 = new Table(1, 4);
        MenuItem shrimp = new MenuItem(1, "Tôm hấp", 250000, "kg");

        Order order1 = orderService.createOrder(t1);
        orderService.addItem(order1, shrimp, 2);
        orderService.removeItem(order1, 0);
    }
}
