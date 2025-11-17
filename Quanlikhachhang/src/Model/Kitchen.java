package Model;

import java.util.HashMap;
import java.util.Map;

public class Kitchen {
    private int id;
    private String status; // Idle, Cooking

    private Map<OrderItem, String> cookingOrders;

    public Kitchen(int id) {
        this.id = id;
        this.status = "Idle";
        this.cookingOrders = new HashMap<>();
    }

    public void startCooking(OrderItem item) {
        cookingOrders.put(item, "Cooking");
        status = "Cooking";
        System.out.println("Bếp bắt đầu chế biến " + item.getMenuItem().getName());
    }

    public void finishCooking(OrderItem item) {
        cookingOrders.put(item, "Finished");
        System.out.println(item.getMenuItem().getName() + " đã chế biến xong!");
        if(allFinished()) {
            status = "Idle";
        }
    }

    public void checkProgress() {
        System.out.println("=== Tiến độ bếp ===");
        for(OrderItem item : cookingOrders.keySet()) {
            System.out.println(item.getMenuItem().getName() + " : " + cookingOrders.get(item));
        }
    }

    private boolean allFinished() {
        for(String s : cookingOrders.values()) {
            if(!s.equals("Finished")) return false;
        }
        return true;
    }
}
