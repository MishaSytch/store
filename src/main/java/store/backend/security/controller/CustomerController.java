package store.backend.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.Customer;
import store.backend.database.entity.Order;
import store.backend.database.repository.CustomerRepository;
import store.backend.database.repository.OrderRepository;

import java.util.Optional;

@RestController
@RequestMapping("/account/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{id}")
    public Optional<Customer> getCustomerByEmail(@PathVariable("id") Long id) {
        return customerRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Customer putCustomer(@PathVariable("id") Long id, @RequestBody Customer editedCustomer) {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer != null) {
            customer.setFirstName(editedCustomer.getFirstName());
            customer.setLastName(editedCustomer.getLastName());
            customer.setPassword(editedCustomer.getPassword());
            customer.setEmail(editedCustomer.getEmail());
            customer.setRole(editedCustomer.getRole());
            customerRepository.save(customer);
        }

        return customer;
    }


    @GetMapping("/{id}/order")
    public Iterable<Order> getCustomerOrders(@PathVariable("id") Long user_id) {
        return orderRepository.findAllByCustomerId(user_id);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }
}
