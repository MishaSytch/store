package store.backend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.User;
import store.backend.database.entity.Order;
import store.backend.database.entity.Product;
import store.backend.database.repository.OrderRepository;
import store.backend.service.product.ProductService;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    public Order createOrder(User user, Date date, Iterable<Product> products) {
        Order order = Order.builder()
                .date(date)
                .build();

            orderRepository.save(addOrder(user, order));
            addProduct(order, products);

        return order;

    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order addOrder(User user, Order order) {
        user.addOrder(order);

        return  order;
    }

    public void addProduct(Order order, Iterable<Product> products) {
        for (Product product : products) {
            order.addProduct(product);
        }
    }

    public Order getOrder(Long order_id) {
        return orderRepository.findById(order_id).orElse(null);
    }

    public Iterable<Order> getOrders(Long customer_id) {
        return orderRepository.findAll().stream().filter(order -> order.getUser().getId().equals(customer_id)).collect(Collectors.toList());
    }

    public void deleteOrder(Order order) {
        order.getUser().removeOrder(order);
    }
}
