package Service;

import Model.Customer;
import Model.Reservation;
import Model.Table;

import java.time.LocalDateTime;
import java.util.List;

public class ReservationService {
    private List<Reservation> reservations;

    public ReservationService(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Reservation createReservation(Customer customer, Table table, LocalDateTime time, int peopleCount) {
        Reservation res = new Reservation(reservations.size() + 1, customer, table, time, peopleCount);
        reservations.add(res);
        res.confirm();
        return res;
    }

    public void cancelReservation(Reservation res) {
        res.cancel();
        reservations.remove(res);
    }
}
