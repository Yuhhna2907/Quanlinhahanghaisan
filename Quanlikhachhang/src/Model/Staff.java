package Model;

public class Staff {
    private int id;
    private String name;
    private String role; // Waiter, Cashier, Manager

    public Staff(int id, String name, String role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }

    public void takeOrder(Order order, MenuItem item, int qty) {
        order.addItem(item, qty);
        System.out.println(name + " đã thêm " + qty + " " + item.getName() + " vào order.");
    }

    public void cancelOrderItem(Order order, int index) {
        order.removeItem(index);
        System.out.println(name + " đã hủy 1 món trong order.");
    }

    public void moveTable(Order order, Table newTable) {
        Table oldTable = order.getTable();
        if(newTable.getStatus().equals("Available")) {
            oldTable.free();
            newTable.occupy();
            System.out.println(name + " đã chuyển order từ bàn " + oldTable.getId() + " sang bàn " + newTable.getId());
        } else {
            System.out.println("Bàn mới không khả dụng, không thể chuyển order.");
        }
    }

    public String getRole() { return role; }
    public String getName() { return name; }
}
