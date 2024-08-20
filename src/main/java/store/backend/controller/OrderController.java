package store.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.Customer;
import store.backend.database.entity.Order;
import store.backend.database.entity.SKU;
import store.backend.database.repository.CustomerRepository;
import store.backend.database.repository.OrderRepository;
import store.backend.service.OrderService;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public Iterable<Order> getOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Order> getOrder(@PathVariable Long id) {
        return orderRepository.findById(id);
    }

    @GetMapping("/")
    public Iterable<Order> getOrderByCustomerId(@RequestParam Long customer_id) {
        return orderRepository.findAllByCustomerId(customer_id);
    }

    @PostMapping
    public Order postOrder(@RequestParam("customer_id") Long customer_id, @RequestBody Iterable<SKU> skus) {
        Optional<Customer>  customer = customerRepository.findById(customer_id);
        return customer.map(value -> orderService.createOrder(value, skus, new Date())).orElse(null);
    }
}
