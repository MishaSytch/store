package store.backend.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.Customer;
import store.backend.database.entity.Order;
import store.backend.database.repository.CustomerRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OrderService orderService;

    public Optional<Customer> getCustomer(Long customer_id) {
        return customerRepository.findById(customer_id);
    }

    public Customer updateCustomer(Long customer_id, Customer editedCustomer) {
        return customerRepository.findById(customer_id)
                .map(
                        customer -> {
                            customer.setFirstName(editedCustomer.getFirstName());
                            customer.setLastName(editedCustomer.getLastName());
                            customer.setPassword(editedCustomer.getPassword());
                            customer.setEmail(editedCustomer.getEmail());
                            customer.setRole(editedCustomer.getRole());

                            return customerRepository.save(customer);
                        }
                ).orElse(null);
    }

    public Iterable<Order> getOrders(Long customer_id) {
        return customerRepository.findOrdersByCustomer_id(customer_id);
    }

    public Order getOrder(Long customer_id, Long order_id) {
        Iterable<Order> orders = customerRepository.findOrdersByCustomer_id(customer_id);
        for (Order order : orders) {
            if (order.getId().equals(order_id)) return order;
        }
        return null;
    }

    public Order addOrder(Long customer_id, Order order) {
        return customerRepository.findById(customer_id)
                .map(
                        customer -> {
                            customer.addOrder(order);
                            return order;
                        }
                )
                .orElse(null);
    }

    public void deleteOrder(@PathVariable("id") Long customer_id, @RequestParam Long order_id) {
        if (getOrder(customer_id, order_id) != null) orderService.deleteOrder(order_id);
    }

//    Security

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public Customer create(Customer customer) {
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(customer);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public Customer getByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByEmail;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public Customer getCurrentCustomer() {
        // Получение имени пользователя из контекста Spring Security
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(username);
    }
}
