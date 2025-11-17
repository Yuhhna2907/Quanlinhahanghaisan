import Repository.CustomerRepository;
import Repository.OrderRepository;
import Repository.TableRepository;
import Model.*;
import Service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainCustomerInteractive {
    public static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.println(prompt);
            String line = sc.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ!");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ===== Khởi tạo Repository & Service =====
        CustomerRepository customerRepo = new CustomerRepository();
        TableRepository tableRepo = new TableRepository();
        OrderRepository orderRepo = new OrderRepository();

        ReservationService resService = new ReservationService(new ArrayList<>());
        List<InventoryItem> inventoryList = new ArrayList<>();
        List<InventoryLog> inventoryLogs = new ArrayList<>();
        InventoryService inventoryService = new InventoryService(inventoryList, inventoryLogs);
        OrderService orderService = new OrderService(new ArrayList<>());
        KitchenService kitchenService = new KitchenService(new Kitchen(1));
        PaymentService paymentService = new PaymentService(new ArrayList<>());

        // ===== Tạo bàn sẵn nếu chưa có =====
        if (tableRepo.getAllTables().isEmpty()) {
            Table t1 = new Table(1, 4);
            Table t2 = new Table(2, 6);
            tableRepo.saveTable(t1);
            tableRepo.saveTable(t2);
        }

        // ===== Tạo kho mẫu nếu chưa có =====
        if (inventoryList.isEmpty()) {
            inventoryList.add(new InventoryItem(1, "Tôm", 10, "kg", 2));
            inventoryList.add(new InventoryItem(2, "Cua", 5, "kg", 1));
        }

        // ===== Menu =====
        List<MenuItem> menu = new ArrayList<>();
        menu.add(new MenuItem(1, "Tôm hấp", 250000, "kg"));
        menu.add(new MenuItem(2, "Cua rang me", 300000, "kg"));

        // ===== 1. Customer đăng ký =====
        System.out.println("=== Đăng ký khách hàng ===");
        System.out.print("Nhập tên khách hàng: ");
        String name = sc.nextLine().trim();
        System.out.print("Nhập số điện thoại: ");
        String phone = sc.nextLine().trim();

        Customer customer = new Customer(customerRepo.getAllCustomers().size() + 1, name, phone);
        customerRepo.saveCustomer(customer);

        // ===== 2. Customer đặt bàn =====
        System.out.println("\n=== Danh sách bàn trống ===");
        for (Table t : tableRepo.getAllTables()) {
            System.out.println("Bàn " + t.getId() + " - Sức chứa: " + t.getCapacity() + " - Trạng thái: " + t.getStatus());
        }

        int tableId = readInt(sc, "Chọn bàn:");
        Table selectedTable = tableRepo.getAllTables().stream()
                .filter(t -> t.getId() == tableId).findFirst().orElse(null);

        if (selectedTable == null) {
            System.out.println("Bàn không tồn tại, chương trình kết thúc.");
            sc.close();
            return;
        }

        int people = readInt(sc, "Nhập số người:");
        Reservation res = resService.createReservation(customer, selectedTable,
                LocalDateTime.now().plusHours(1), people);

        // ===== 3. Customer tạo order =====
        Order order = orderService.createOrder(selectedTable);
        boolean ordering = true;

        while (true) {
            System.out.println("\n=== Menu ===");
            for (MenuItem m : menu) {
                System.out.println(m.getId() + ". " + m.getName() + " - " + m.getPrice());
            }

            int choice = readInt(sc, "Chọn món (ID), 0 để kết thúc, -1 để xóa món:");

            if (choice == 0) {
                ordering = false;
                break;
            } else if (choice == -1) {
                if (order.getItems().isEmpty()) {
                    System.out.println("Order trống, không có món để xóa.");
                    continue;
                }
                int idx = readInt(sc, "Nhập index món trong order để xóa (0,1,...):");
                orderService.removeItem(order, idx);
                continue;
            }

            MenuItem selectedItem = menu.stream().filter(m -> m.getId() == choice).findFirst().orElse(null);
            if (selectedItem != null) {
                int qty = readInt(sc, "Nhập số lượng:");

                // Kiểm kho
                InventoryItem invItem = inventoryList.stream()
                        .filter(i -> i.getName().equals(selectedItem.getName().split(" ")[0]))
                        .findFirst().orElse(null);

                if (invItem != null && invItem.getQuantity() >= qty) {
                    inventoryService.useItem(invItem, qty);
                    orderService.addItem(order, selectedItem, qty);
                    kitchenService.startCooking(new OrderItem(selectedItem, qty));
                } else {
                    System.out.println("Nguyên liệu không đủ để chế biến món này!");
                }
            } else {
                System.out.println("Món không tồn tại.");
            }
        }

        // ===== 4. Chế biến món =====
        kitchenService.checkProgress();
        for (OrderItem oi : order.getItems()) {
            kitchenService.finishCooking(oi);
        }
        System.out.println("=== Tất cả món đã hoàn thành ===");

        // ===== 5. Thanh toán =====
        paymentService.processPayment(order, "Cash");

        // ===== 6. Lưu order =====
        orderRepo.saveOrder(order);

        // Giải phóng bàn
        selectedTable.free();

        System.out.println("Cảm ơn " + customer.getName() + ", chúc bạn ngon miệng!");
        sc.close();
    }
}
