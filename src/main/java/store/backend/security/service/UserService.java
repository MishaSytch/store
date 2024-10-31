package store.backend.security.service;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import store.backend.database.entity.Product;
import store.backend.database.entity.User;
import store.backend.database.entity.Order;
import store.backend.database.repository.UserRepository;
import store.backend.service.product.IEmailService;
import store.backend.service.product.ProductService;

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
    @Autowired
    private IEmailService IEmailService;
    @Autowired
    private ProductService productService;

    public Optional<User> getUser(Long user_id) {
        return userRepository.findById(user_id);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user) {
        return userRepository.save(user);
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

    public Order addOrder(User user, Order order) {
                            user.addOrder(order);
                            StringBuilder stringBuilder = new StringBuilder("Дорогой " + user.getFirstName() + "\nВаш заказ успешно оформлен:\n\t");
                            List<Product> products =  order.getProducts();
                            products.forEach(
                                product ->
                                        stringBuilder
                                                .append(product.getName())
                                                .append(": ")
                                                .append(productService.getCurrentPrice(product.getId()).getPrice().toString())
                                                .append("\n\t")
                            );
                            IEmailService.sendSimpleMessage(user.getEmail(), "Ваш заказ в интернет магазине", stringBuilder.toString());

                            return order;
    }

    public void deleteOrder(User user, @RequestParam Long order_id) {
        Order order;
        if ((order = orderService.getOrder(order_id)) != null) user.removeOrder(order);
    }

    public void deleteUser(Long user_id) {
        userRepository.deleteById(user_id);
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
