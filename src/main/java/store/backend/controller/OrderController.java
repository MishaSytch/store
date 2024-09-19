package store.backend.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import store.backend.database.entity.Order;
import store.backend.database.entity.Product;
import store.backend.database.entity.User;
import store.backend.security.service.OrderService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @AllArgsConstructor
    private static class Data {
        @JsonProperty("user")
        User user;

        @JsonProperty("products")
        List<Product> products;
    }

    @PostMapping("/create")
    public Order createOrder(@RequestBody Data data) {
        return orderService.createOrder(data.user, new Date(), data.products);
    }

}
