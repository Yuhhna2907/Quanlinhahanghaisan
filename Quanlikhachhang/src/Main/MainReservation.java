package Main;

import Model.Customer;
import Model.Reservation;
import Model.Table;
import Service.ReservationService;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class MainReservation {
    public static void main(String[] args) {
        ReservationService resService = new ReservationService(new ArrayList<>());

        Customer c1 = new Customer(1, "Nguyen Van A", "0912345678");
        Table t1 = new Table(1, 4);

        // Đặt bàn
        Reservation res1 = resService.createReservation(c1, t1, LocalDateTime.now().plusHours(1), 3);

        // Hủy bàn
        resService.cancelReservation(res1);
    }
}
