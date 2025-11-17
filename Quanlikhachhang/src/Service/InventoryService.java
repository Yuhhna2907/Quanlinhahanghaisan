package Service;

import Model.InventoryItem;
import Model.InventoryLog;

import java.util.List;

public class InventoryService {
    private List<InventoryItem> items;
    private List<InventoryLog> logs;

    public InventoryService(List<InventoryItem> items, List<InventoryLog> logs) {
        this.items = items;
        this.logs = logs;
    }

    public void importItem(InventoryItem item, double quantity) {
        item.increase(quantity);
        InventoryLog log = new InventoryLog(logs.size() + 1, item, quantity, "Import");
        logs.add(log);
        log.printLog();
    }

    public boolean useItem(InventoryItem item, double quantity) {
        boolean ok = item.decrease(quantity);
        if(ok) {
            InventoryLog log = new InventoryLog(logs.size() + 1, item, -quantity, "Export");
            logs.add(log);
            log.printLog();
        }
        return ok;
    }

    public void checkStock() {
        System.out.println("=== Kiểm kho ===");
        for(InventoryItem item : items) {
            System.out.println(item.getName() + " : " + item.getQuantity());
            if(item.needRestock()) {
                System.out.println("Cảnh báo: " + item.getName() + " sắp hết!");
            }
        }
    }
}
