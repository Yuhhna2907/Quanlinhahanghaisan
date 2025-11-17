package Model;

import java.time.LocalDateTime;

public class Reservation {
    private int id;
    private Customer customer;
    private Table table;
    private LocalDateTime time;
    private int peopleCount;
    private String status; // Pending, Confirmed, Cancelled

    public Reservation(int id, Customer customer, Table table, LocalDateTime time, int peopleCount) {
        this.id = id;
        this.customer = customer;
        this.table = table;
        this.time = time;
        this.peopleCount = peopleCount;
        this.status = "Pending";
    }

    public void confirm() {
        if(table.getStatus().equals("Available")) {
            table.reserve();
            status = "Confirmed";
            System.out.println("Reservation #" + id + " đã được xác nhận.");
        } else {
            System.out.println("Bàn không khả dụng, không thể xác nhận.");
        }
    }

    public void cancel() {
        if(status.equals("Confirmed")) {
            table.free();
        }
        status = "Cancelled";
        System.out.println("Reservation #" + id + " đã bị hủy.");
    }

    public String getStatus() { return status; }
    public Table getTable() { return table; }
    public Customer getCustomer() { return customer; }
}
