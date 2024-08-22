package store.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.Customer;
import store.backend.database.entity.Order;
import store.backend.database.entity.SKU;
import store.backend.database.repository.OrderRepository;
import store.backend.database.repository.SKURepository;

import java.util.Date;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Customer customer, Iterable<SKU> skus, Date date) {
        Order order = Order.builder()
                .customer(customer)
                .date(date)
                .build();
        for (SKU sku : skus) order.addSKU(sku);

        return orderRepository.save(order);
    }

    public Order addOrder(Customer customer, Order order) {
        customer.addOrder(order);

        return  order;
    }

    public Order updateOrder(Long order_id, Order editedOrder) {
        return orderRepository.findById(order_id)
                .map(
                        order -> {
                            order.setSkus(editedOrder.getSkus());

                            return order;
                        }
                ).orElse(null);
    }

    public void deleteOrder(Long order_id) {
        orderRepository.findById(order_id).ifPresent(
                order -> order.getCustomer().removeOrder(order)
        );
    }
}
