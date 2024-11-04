package store.backend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.backend.database.entity.User;
import store.backend.database.entity.Order;
import store.backend.database.entity.Product;
import store.backend.database.repository.OrderRepository;

import java.util.Date;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order createOrder(User user, Date date, Iterable<Product> products) {
        Order order = Order.builder()
                .date(date)
                .build();

            orderRepository.save(addOrder(user, order));
            addProduct(order, products);

        return updateOrder(order);
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Transactional
    public Order addOrder(User user, Order order) {
        user.addOrder(order);

        return order;
    }

    @Transactional
    public Order updateOrder(Order order) {
        orderRepository.findById(order.getId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        saveOrder(order);

        return order;
    }

    @Transactional
    public Order addProduct(Order order, Iterable<Product> products) {
        for (Product product : products) {
            order.addProduct(product);
        }

        return updateOrder(order);
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
