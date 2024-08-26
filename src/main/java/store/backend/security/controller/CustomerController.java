package store.backend.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.User;
import store.backend.database.entity.Order;
import store.backend.security.service.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/account/customer")
public class CustomerController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public Optional<User> getCustomerById(@PathVariable("id") Long user_id) {
        return userService.getUser(user_id);
    }

    @PutMapping("/{id}")
    public User putCustomer(@PathVariable("id") Long user_id, @RequestBody User editedUser) {
        return userService.updateUser(user_id, editedUser);
    }

    @GetMapping("/{id}/orders")
    public Iterable<Order> getOrders(@PathVariable("id") Long user_id) {
        return userService.getOrders(user_id);
    }

    @GetMapping("/{id}/order")
    public Order getOrder(@PathVariable("id") Long user_id, @RequestParam Long order_id) {
        return userService.getOrder(user_id, order_id);
    }

    @PostMapping("/{id}/add/order")
    public Order postOrder(@PathVariable("id") Long user_id, @RequestBody Order order) {
        return userService.addOrder(user_id, order);
    }

    @DeleteMapping("/{id}/delete/order")
    public void deleteOrder(@PathVariable("id") Long user_id, @RequestParam Long order_id) {
        userService.deleteOrder(user_id, order_id);
    }
}
