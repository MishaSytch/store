package store.backend.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.Customer;
import store.backend.database.entity.Order;
import store.backend.database.repository.CustomerRepository;
import store.backend.database.repository.OrderRepository;

@RestController
@RequestMapping("/account/customer")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{email}")
    public Customer getCustomerByEmail(@PathVariable("email") String email) {
        return customerRepository.findByEmail(email).orElse(null);
    }

    @GetMapping("/{id}")
    public Iterable<Order> getCustomerOrdersById(@PathVariable("id") Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);

        return customer != null ? customer.getOrders() : null;
    }

    @GetMapping("/{id}/order/{order_id}")
    public Order getCustomerOrder(@PathVariable("id") Long user_id, @PathVariable("order_id") Long order_id) {
        Order order =  orderRepository.findById(order_id).orElse(null);
        return order != null && order.getCustomer().getId().equals(user_id)
                    ? order
                    : null;
    }

    @PostMapping("/{id}")
    public Customer editCustomerEmailById(@PathVariable("id") Long id, String email) {
        Customer customer = customerRepository.findById(id).orElse(null);

        if (customer != null) {
            customerRepository.deleteById(id);
            customer.setEmail(email);
            customerRepository.save(customer);
        }

        return customer;
    }
}
