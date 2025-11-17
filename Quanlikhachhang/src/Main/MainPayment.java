package Main;

import Model.MenuItem;
import Model.Order;
import Model.Payment;
import Model.Table;
import Service.PaymentService;

import java.util.ArrayList;
import java.util.List;

public class MainPayment {
    public static void main(String[] args) {
            List<Payment> payments = new ArrayList<>();
            PaymentService paymentService = new PaymentService(payments);

            Table t1 = new Table(1, 4);
            Order order1 = new Order(1, t1);
            MenuItem shrimp = new MenuItem(1, "Tôm hấp", 250000, "kg");
            order1.addItem(shrimp, 2);

            paymentService.processPayment(order1, "Cash");
        }
    }

