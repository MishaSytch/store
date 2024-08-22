package store.backend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.Customer;
import store.backend.database.entity.Order;
import store.backend.database.entity.Product;
import store.backend.database.repository.OrderRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Customer customer, Date date) {
        Order order = Order.builder()
                .customer(customer)
                .date(date)
                .build();

        return orderRepository.save(order);
    }

    public Order addOrder(Customer customer, Order order) {
        customer.addOrder(order);

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

    public void deleteOrder(Long order_id) {
        orderRepository.findById(order_id).ifPresent(
                order -> order.getCustomer().removeOrder(order)
        );
    }
}
