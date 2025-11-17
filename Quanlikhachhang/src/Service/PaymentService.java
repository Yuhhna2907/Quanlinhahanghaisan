package Service;

import Model.Order;
import Model.Payment;

import java.util.List;

public class PaymentService {
    private List<Payment> payments;

    public PaymentService(List<Payment> payments) {
        this.payments = payments;
    }

    public Payment processPayment(Order order, String method) {
        Payment payment = new Payment(payments.size() + 1, order, method);
        payment.process();
        payments.add(payment);
        return payment;
    }
}
