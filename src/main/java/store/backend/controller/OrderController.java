package store.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.Order;
import store.backend.database.repository.OrderRepository;

import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

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
}
