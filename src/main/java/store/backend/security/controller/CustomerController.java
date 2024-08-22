package store.backend.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.Customer;
import store.backend.database.entity.Order;
import store.backend.security.service.CustomerService;

import java.util.Optional;

@RestController
@RequestMapping("/account/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}")
    public Optional<Customer> getCustomerById(@PathVariable("id") Long customer_id) {
        return customerService.getCustomer(customer_id);
    }

    @PutMapping("/{id}")
    public Customer putCustomer(@PathVariable("id") Long customer_id, @RequestBody Customer editedCustomer) {
        return customerService.updateCustomer(customer_id, editedCustomer);
    }

    @GetMapping("/{id}/orders")
    public Iterable<Order> getOrders(@PathVariable("id") Long customer_id) {
        return customerService.getOrders(customer_id);
    }

    @GetMapping("/{id}/order")
    public Order getOrder(@PathVariable("id") Long customer_id, @RequestParam Long order_id) {
        return customerService.getOrder(customer_id, order_id);
    }

    @PostMapping("/{id}/add/order")
    public Order postOrder(@PathVariable("id") Long customer_id, @RequestBody Order order) {
        return customerService.addOrder(customer_id, order);
    }

    @DeleteMapping("/{id}/delete/order")
    public void deleteOrder(@PathVariable("id") Long customer_id, @RequestParam Long order_id) {
        customerService.deleteOrder(customer_id, order_id);
    }
}
