package Main;

import Data.CustomerRepository;
import Model.Customer;

import java.util.List;

public class MainCustomer {
    public static void main(String[] args) {
        CustomerRepository customerRepo = new CustomerRepository();

        Customer c1 = new Customer(1, "Nguyen Van A", "0912345678");
        Customer c2 = new Customer(2, "Tran Thi B", "0987654321");

        customerRepo.saveCustomer(c1);
        customerRepo.saveCustomer(c2);

        List<Customer> customers = customerRepo.getAllCustomers();
        System.out.println("=== Danh sách khách hàng ===");
        for (Customer c : customers) {
            System.out.println(c.getId() + " - " + c.getName() + " - " + c.getPhone());
        }
    }
}
