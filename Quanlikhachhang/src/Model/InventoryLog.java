package Model;

import java.time.LocalDateTime;

public class InventoryLog {
    private int id;
    private InventoryItem item;
    private double change;
    private String type; // Import / Export / Adjust
    private LocalDateTime time;

    public InventoryLog(int id, InventoryItem item, double change, String type) {
        this.id = id;
        this.item = item;
        this.change = change;
        this.type = type;
        this.time = LocalDateTime.now();
    }

    public void printLog() {
        System.out.println("[" + time + "] " + type + " " + change + " " + item.getName());
    }
}
