import Model.*;
import Repository.OrderRepository;
import Repository.TableRepository;
import Service.InventoryService;
import Service.KitchenService;
import Service.OrderService;
import Service.PaymentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainStaffInteractive {
    public static int readInt(Scanner sc, String prompt) {
        while(true) {
            System.out.println(prompt);
            String line = sc.nextLine();
            try {
                return Integer.parseInt(line.trim());
            } catch(NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ!");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ===== Khởi tạo repository & service =====
        TableRepository tableRepo = new TableRepository();
        OrderRepository orderRepo = new OrderRepository();

        List<InventoryItem> inventoryList = new ArrayList<>();
        List<InventoryLog> inventoryLogs = new ArrayList<>();
        InventoryService inventoryService = new InventoryService(inventoryList, inventoryLogs);

        OrderService orderService = new OrderService(new ArrayList<>());
        KitchenService kitchenService = new KitchenService(new Kitchen(1));
        PaymentService paymentService = new PaymentService(new ArrayList<>());

        // ===== Tạo bàn & kho mẫu nếu chưa có =====
        if(tableRepo.getAllTables().isEmpty()) {
            tableRepo.saveTable(new Table(1, 4));
            tableRepo.saveTable(new Table(2, 6));
        }
        if(inventoryList.isEmpty()) {
            inventoryList.add(new InventoryItem(1, "Tôm", 10, "kg", 2));
            inventoryList.add(new InventoryItem(2, "Cua", 5, "kg", 1));
        }

        // ===== Menu =====
        List<MenuItem> menu = new ArrayList<>();
        menu.add(new MenuItem(1, "Tôm hấp", 250000, "kg"));
        menu.add(new MenuItem(2, "Cua rang me", 300000, "kg"));

        boolean running = true;
        while(running) {
            System.out.println("\n=== Staff Menu ===");
            System.out.println("1. Xem bàn / Chuyển bàn");
            System.out.println("2. Quản lý Order");
            System.out.println("3. Quản lý kho");
            System.out.println("4. Quản lý bếp");
            System.out.println("5. Thanh toán cho khách");
            System.out.println("0. Thoát");

            int choice = readInt(sc, "Chọn chức năng:");

            switch(choice) {
                case 1: // Xem bàn / chuyển bàn
                    System.out.println("=== Danh sách bàn ===");
                    for(Table t : tableRepo.getAllTables()) {
                        System.out.println("Bàn " + t.getId() + " - Sức chứa: " + t.getCapacity() + " - Trạng thái: " + t.getStatus());
                    }
                    int tableId = readInt(sc, "Chọn bàn để chuyển trạng thái (0 để bỏ qua):");
                    if(tableId != 0) {
                        Table t = tableRepo.getAllTables().stream()
                                .filter(tab -> tab.getId() == tableId).findFirst().orElse(null);
                        if(t != null) {
                            if(t.getStatus().equals("Trống")) t.occupy();
                            else t.free();
                            System.out.println("Cập nhật trạng thái bàn xong.");
                        }
                    }
                    break;

                case 2: // Quản lý order
                    if(orderRepo.getAllOrders().isEmpty()) {
                        System.out.println("Chưa có order nào.");
                        break;
                    }
                    for(int i=0; i<orderRepo.getAllOrders().size(); i++) {
                        Order o = orderRepo.getAllOrders().get(i);
                        System.out.println(i + ": Bàn " + o.getTable().getId() + " - " + o.getItems().size() + " món");
                    }
                    int orderIdx = readInt(sc, "Chọn order để chỉnh sửa (0..n, -1 để bỏ qua):");
                    if(orderIdx >=0 && orderIdx < orderRepo.getAllOrders().size()) {
                        Order o = orderRepo.getAllOrders().get(orderIdx);
                        System.out.println("1. Thêm món  2. Xóa món");
                        int subChoice = readInt(sc, "Chọn thao tác:");
                        if(subChoice == 1) {
                            for(MenuItem m : menu) System.out.println(m.getId() + ". " + m.getName() + " - " + m.getPrice());
                            int mId = readInt(sc, "Chọn món để thêm:");
                            MenuItem mi = menu.stream().filter(x->x.getId()==mId).findFirst().orElse(null);
                            if(mi != null) {
                                int qty = readInt(sc, "Nhập số lượng:");
                                o.addItem(new OrderItem(mi, qty));
                                System.out.println("Đã thêm món.");
                            }
                        } else if(subChoice == 2) {
                            int idx = readInt(sc, "Nhập index món trong order để xóa:");
                            if(idx>=0 && idx<o.getItems().size()) {
                                o.getItems().remove(idx);
                                System.out.println("Đã xóa món.");
                            }
                        }
                    }
                    break;

                case 3: // Quản lý kho
                    System.out.println("=== Kho hiện tại ===");
                    for(InventoryItem item : inventoryList) {
                        System.out.println(item.getId() + ". " + item.getName() + " - " + item.getQuantity() + " " + item.getUnit());
                    }
                    int invChoice = readInt(sc, "1. Thêm nguyên liệu, 0 để bỏ qua:");
                    if(invChoice == 1) {
                        int invId = readInt(sc, "Chọn ID nguyên liệu:");
                        InventoryItem inv = inventoryList.stream().filter(i->i.getId()==invId).findFirst().orElse(null);
                        if(inv!=null) {
                            int qty = readInt(sc, "Nhập số lượng thêm:");
                            inv.setQuantity(inv.getQuantity() + qty);
                            System.out.println("Cập nhật kho xong.");
                        }
                    }
                    break;

                case 4: // Quản lý bếp
                    System.out.println("=== Trạng thái bếp ===");
                    for(Order o : orderRepo.getAllOrders()) {
                        for(OrderItem oi : o.getItems()) {
                            System.out.println("Bàn " + o.getTable().getId() + " - " + oi.getMenuItem().getName() + " - " + oi.getQuantity() + " - " + (oi.isCooked()?"Hoàn thành":"Đang nấu"));
                        }
                    }
                    int finishChoice = readInt(sc, "Nhập 1 để đánh dấu món hoàn thành, 0 để bỏ qua:");
                    if(finishChoice ==1) {
                        for(Order o : orderRepo.getAllOrders()) {
                            for(OrderItem oi : o.getItems()) {
                                oi.setCooked(true);
                            }
                        }
                        System.out.println("Tất cả món được đánh dấu hoàn thành.");
                    }
                    break;

                case 5: // Thanh toán
                    if(orderRepo.getAllOrders().isEmpty()) {
                        System.out.println("Chưa có order để thanh toán.");
                        break;
                    }
                    for(int i=0;i<orderRepo.getAllOrders().size();i++) {
                        Order o = orderRepo.getAllOrders().get(i);
                        System.out.println(i + ": Bàn " + o.getTable().getId() + " - " + o.getItems().size() + " món");
                    }
                    int payIdx = readInt(sc, "Chọn order để thanh toán:");
                    if(payIdx>=0 && payIdx<orderRepo.getAllOrders().size()) {
                        Order o = orderRepo.getAllOrders().get(payIdx);
                        paymentService.processPayment(o, "Cash");
                        o.getTable().free();
                        System.out.println("Thanh toán xong.");
                    }
                    break;

                case 0:
                    running = false;
                    break;

                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }

        System.out.println("Thoát hệ thống Staff.");
        sc.close();
    }
}
