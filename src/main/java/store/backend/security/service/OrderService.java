package store.backend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.User;
import store.backend.database.entity.Order;
import store.backend.database.entity.Product;
import store.backend.database.repository.OrderRepository;
import store.backend.service.product.ProductService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    public Order createOrder(Long user_id, Date date, List<Long> product_id) {
        Order order = Order.builder()
                .date(date)
                .build();
        orderRepository.save(addOrder(userService.getUser(user_id).get(), order));
        addProduct(order.getId(), productService.getProducts(product_id));

        return order;

    }

    public Order addOrder(User user, Order order) {
        user.addOrder(order);

        return  order;
    }

    public void addProduct(Long order_id, List<Product> products) {
        Optional<Order> order = orderRepository.findById(order_id);

        if (order.isPresent()) {
            for (Product product : products) {
                order.get().addProduct(product);
            }
        }
    }

    public Order getOrder(Long order_id) {
        return orderRepository.findById(order_id).orElse(null);
    }

    public Iterable<Order> getOrders(Long customer_id) {
        return orderRepository.findAll().stream().filter(order -> order.getUser().getId().equals(customer_id)).collect(Collectors.toList());
    }

    public void deleteOrder(Long order_id) {
        orderRepository.findById(order_id).ifPresent(
                order -> order.getUser().removeOrder(order)
        );
    }
}
