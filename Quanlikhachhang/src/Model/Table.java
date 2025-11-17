package Model;

public class Table {
    private int id;
    private int capacity;
    private String status; // Available, Occupied, Reserved

    public Table(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.status = "Available";
    }

    public void reserve() { this.status = "Reserved"; }
    public void occupy() { this.status = "Occupied"; }
    public void free() { this.status = "Available"; }

    public int getId() { return id; }
    public String getStatus() { return status; }
    public  int getCapacity() { return capacity; }
}
