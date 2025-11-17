package Model;

import java.time.LocalDateTime;

public class Payment {
    private int id;
    private Order order;
    private double amount;
    private String method; // Cash / Card / MoMo
    private LocalDateTime time;

    public Payment(int id, Order order, String method) {
        this.id = id;
        this.order = order;
        this.amount = order.calculateTotal();
        this.method = method;
        this.time = LocalDateTime.now();
    }

    public void process() {
        if(order.getStatus().equals("Completed")) {
            System.out.println("Order #" + order.getTable().getId() + " đã thanh toán rồi!");
        } else {
            System.out.println("Đang thanh toán Order #" + order.getTable().getId());
            System.out.println("Số tiền: " + amount + " VND, Phương thức: " + method);
            order.checkout(); // Hoàn tất order
            time = LocalDateTime.now();
            System.out.println("Thanh toán hoàn tất lúc " + time);
        }
    }

    // Getter
    public double getAmount() { return amount; }
    public String getMethod() { return method; }
}
