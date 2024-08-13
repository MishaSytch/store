package store.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.backend.database.entity.Customer;
import store.backend.database.repository.CustomerRepository;

@RestController
@RequestMapping("/account")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable("id") Long id) {
        return customerRepository.getById(id);
    }

    @GetMapping("/{email}")
    public Customer getCustomerByEmail(@PathVariable("email") String email) {
        return customerRepository.getByEmail(email);
    }

    @GetMapping
    public Iterable<Customer> getCustomers() {
        return customerRepository.findAll();
    }
}
