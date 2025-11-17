package Main;

import Model.InventoryItem;
import Model.InventoryLog;
import Service.InventoryService;

import java.util.ArrayList;
import java.util.List;

public class MainInventory {
    public static void main(String[] args) {
        // Danh sách kho và log
        List<InventoryItem> inventoryItems = new ArrayList<>();
        List<InventoryLog> inventoryLogs = new ArrayList<>();

        InventoryService inventoryService = new InventoryService(inventoryItems, inventoryLogs);

        // Tạo hải sản trong kho
        InventoryItem shrimp = new InventoryItem(1, "Tôm", 10, "kg", 2);
        InventoryItem crab = new InventoryItem(2, "Cua", 5, "kg", 1);

        inventoryItems.add(shrimp);
        inventoryItems.add(crab);

        // Nhập kho
        inventoryService.importItem(shrimp, 5);
        inventoryService.importItem(crab, 3);

        // Dùng hải sản
        inventoryService.useItem(shrimp, 8);
        inventoryService.useItem(crab, 2);

        // Kiểm kho
        inventoryService.checkStock();
    }

}
