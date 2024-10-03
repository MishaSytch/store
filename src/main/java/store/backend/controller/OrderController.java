package store.backend.controller;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.backend.database.entity.Order;
import store.backend.database.entity.Product;
import store.backend.database.entity.User;
import store.backend.security.service.OrderService;
import store.backend.security.service.UserService;
import store.backend.service.product.ProductService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class Data {
        private User user;
        private List<Product> products;
    }

    @PostMapping("/create")
    public Order createOrder(@RequestBody Data data) {
        return userService.addOrder(data.user, orderService.createOrder(data.user, new Date(), data.products));
    }
}
