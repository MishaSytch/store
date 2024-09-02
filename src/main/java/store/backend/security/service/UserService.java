package store.backend.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.User;
import store.backend.database.entity.Order;
import store.backend.database.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderService orderService;

    public Optional<User> getUser(Long user_id) {
        return userRepository.findById(user_id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long user_id, User editedUser) {
        return userRepository.findById(user_id)
                .map(
                        user -> {
                            user.setFirstName(editedUser.getFirstName());
                            user.setLastName(editedUser.getLastName());
                            user.setPassword(editedUser.getPassword());
                            user.setEmail(editedUser.getEmail());
                            user.setRole(editedUser.getRole());

                            return userRepository.save(user);
                        }
                ).orElse(null);
    }

    public Iterable<Order> getOrders(Long customer_id) {
        return orderService.getOrders(customer_id);
    }

    public Order getOrder(Long user_id, Long order_id) {
        Iterable<Order> orders = getOrders(user_id);
        for (Order order : orders) {
            if (order.getId().equals(order_id)) return order;
        }
        return null;
    }

    public Order addOrder(Long user_id, Order order) {
        return userRepository.findById(user_id)
                .map(
                        user -> {
                            user.addOrder(order);
                            return order;
                        }
                )
                .orElse(null);
    }

    public void deleteOrder(@PathVariable("id") Long user_id, @RequestParam Long order_id) {
        if (getOrder(user_id, order_id) != null) orderService.deleteOrder(order_id);
    }

//    Security

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return userRepository.save(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
    public User create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByEmail(String email) {
        return userRepository.findByEmail(email)
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
    public User getCurrentUser() {
        // Получение имени пользователя из контекста Spring Security
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByEmail(username);
    }
}
