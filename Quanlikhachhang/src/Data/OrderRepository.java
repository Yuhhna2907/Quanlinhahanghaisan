package Data;

import Model.Order;
import Model.OrderItem;
import Model.Table;
import Model.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private String filePath = "data/orders.csv";

    public void saveOrder(Order order) {
        // Lưu order: id,tableId,status
        String line = order.getTable().getId() + "," + order.getStatus();
        FileUtil.writeLines(filePath, List.of(line), true);

        // Lưu từng order item: orderId,menuItemName,quantity,price
        for(OrderItem item : order.getItems()) {
            String itemLine = order.getTable().getId() + "," + item.getMenuItem().getName() + "," + item.getQuantity() + "," + item.getSubtotal();
            FileUtil.writeLines("data/order_items.csv", List.of(itemLine), true);
        }
    }

    // Đọc order (demo)
    public List<Order> getAllOrders(List<Table> tables, List<MenuItem> menuItems) {
        List<Order> orders = new ArrayList<>();
        List<String> lines = FileUtil.readLines(filePath);
        for(String line : lines) {
            String[] parts = line.split(",");
            Table table = tables.stream().filter(t -> t.getId() == Integer.parseInt(parts[0])).findFirst().orElse(null);
            Order o = new Order(orders.size() + 1, table);
            if(parts[1].equals("Completed")) o.checkout();
            orders.add(o);
        }
        // Sau đó có thể đọc file order_items.csv để thêm item
        return orders;
    }
}
