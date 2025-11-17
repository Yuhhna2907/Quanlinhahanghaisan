import Data.CustomerRepository;
import Data.OrderRepository;
import Data.TableRepository;
import Model.*;
import Service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainFullDemo {
    public static void main(String[] args) {

        System.out.println("=== Khởi tạo dữ liệu hệ thống ===");

        // ===== Khởi tạo repository =====
        CustomerRepository customerRepo = new CustomerRepository();
        TableRepository tableRepo = new TableRepository();
        OrderRepository orderRepo = new OrderRepository();

        // ===== Khởi tạo Service Layer =====
        ReservationService resService = new ReservationService(new ArrayList<>());
        OrderService orderService = new OrderService(new ArrayList<>());
        PaymentService paymentService = new PaymentService(new ArrayList<>());

        List<InventoryItem> inventoryList = new ArrayList<>();
        List<InventoryLog> inventoryLogs = new ArrayList<>();
        InventoryService inventoryService = new InventoryService(inventoryList, inventoryLogs);

        KitchenService kitchenService = new KitchenService(new Kitchen(1));

        // ===== 1. Tạo khách và bàn =====
        Customer kh1 = new Customer(1, "Nguyen Van A", "0912345678");
        Customer kh2 = new Customer(2, "Tran Thi B", "0987654321");

        customerRepo.saveCustomer(kh1);
        customerRepo.saveCustomer(kh2);

        Table t1 = new Table(1, 4);
        Table t2 = new Table(2, 6);

        tableRepo.saveTable(t1);
        tableRepo.saveTable(t2);

        System.out.println("=== Danh sách khách hàng ===");
        customerRepo.getAllCustomers().forEach(c -> System.out.println(c.getId() + " - " + c.getName()));
        System.out.println("=== Danh sách bàn ===");
        tableRepo.getAllTables().forEach(t -> System.out.println("Bàn " + t.getId() + " - " + t.getStatus()));

        // ===== 2. Đặt bàn =====
        Reservation res1 = resService.createReservation(kh1, t1, LocalDateTime.now().plusHours(1), 3);

        // ===== 3. Tạo Order và thêm món =====
        MenuItem shrimpDish = new MenuItem(1, "Tôm hấp", 250000, "kg");
        MenuItem crabDish = new MenuItem(2, "Cua rang me", 300000, "kg");

        Order order1 = orderService.createOrder(t1);
        orderService.addItem(order1, shrimpDish, 2);
        orderService.addItem(order1, crabDish, 1);

        System.out.println("=== Chi tiết Order ===");
        for(OrderItem item : order1.getItems()) {
            System.out.println(item.getMenuItem().getName() + " x" + item.getQuantity() + " - subtotal: " + item.getSubtotal());
        }

        // ===== 4. Kiểm kho =====
        InventoryItem shrimpInv = new InventoryItem(1, "Tôm", 10, "kg", 2);
        InventoryItem crabInv = new InventoryItem(2, "Cua", 5, "kg", 1);
        inventoryList.add(shrimpInv);
        inventoryList.add(crabInv);

        inventoryService.checkStock();

        inventoryService.useItem(shrimpInv, 4);
        inventoryService.useItem(crabInv, 2);

        // ===== 5. Bếp chế biến =====
        OrderItem oi1 = order1.getItems().get(0);
        OrderItem oi2 = order1.getItems().get(1);

        kitchenService.startCooking(oi1);
        kitchenService.startCooking(oi2);

        kitchenService.checkProgress();

        kitchenService.finishCooking(oi1);
        kitchenService.finishCooking(oi2);

        kitchenService.checkProgress();

        // ===== 6. Thanh toán =====
        paymentService.processPayment(order1, "Cash");

        // ===== 7. Lưu Order vào file =====
        orderRepo.saveOrder(order1);

        System.out.println("=== Full Demo kết thúc ===");
    }

}