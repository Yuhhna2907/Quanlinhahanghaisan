package Data;

import Model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private String filePath = "data/customers.csv";

    public void saveCustomer(Customer c) {
        String line = c.getId() + "," + c.getName() + "," + c.getPhone();
        FileUtil.writeLines(filePath, List.of(line), true);
    }

    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        List<String> lines = FileUtil.readLines(filePath);
        for(String line : lines) {
            String[] parts = line.split(",");
            customers.add(new Customer(
                    Integer.parseInt(parts[0]),
                    parts[1],
                    parts[2]
            ));
        }
        return customers;
    }
}
