package store.backend.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.Customer;
import store.backend.database.entity.Order;
import store.backend.database.entity.Product;
import store.backend.database.repository.OrderRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Customer customer, Date date, Iterable<Product> products) {
        Order order = Order.builder()
                    .customer(customer)
                    .date(date)
                    .products(new ArrayList<>())
                    .build();

        for (Product product : products) {
            order.addProduct(product);
        }

        return orderRepository.save(order);
    }

    public void deleteOrder(Long order_id) {
        orderRepository.findById(order_id).ifPresent(
                order -> order.getCustomer().removeOrder(order)
        );
    }
}
