package store.backend.security.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import store.backend.database.entity.Order;
import store.backend.database.entity.Product;
import store.backend.database.entity.User;
import store.backend.database.repository.OrderRepository;
import store.backend.database.repository.UserRepository;
import store.backend.database.repository.ProductRepository;
import store.backend.security.dto.JwtAuthenticationResponse;
import store.backend.security.dto.SignUpRequest;
import store.backend.security.role.Role;
import store.backend.service.product.ProductService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;


    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    private User testUser;
    private List<Product> testProducts;

    @BeforeEach
    void setUp() {
        SignUpRequest request = new SignUpRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john.doe@example.com");
        request.setPassword("password");

        Role role = Role.CUSTOMER;
        authenticationService.signUp(request, role);

        testUser = userService.getByEmail(request.getEmail());

        Product product1 = productService.createProduct("Product 1", "desc", "sku1", 10L);

        Product product2 = productService.createProduct("Product 2", "desc", "sku2", 10L);

        testProducts = new ArrayList<>();
        testProducts.add(product1);
        testProducts.add(product2);
    }

    @Test
    void createOrder() {
        Order order = orderService.createOrder(testUser, new Date(), testProducts);

        assertNotNull(order);
        assertEquals(testUser, order.getUser());
        assertEquals(2, order.getProducts().size());
    }

    @Test
    void saveOrder() {
        Order order = orderService.createOrder(testUser, new Date(), testProducts);

        assertNotNull(orderRepository.findById(order.getId()).orElse(null));
    }

    @Test
    void addOrder() {
        Order order = new Order();
        order.setDate(new Date());
        order = orderService.addOrder(testUser, order);

        assertTrue(testUser.getOrders().contains(order));
    }

    @Test
    @Transactional
    void addProduct() {
        Order order = orderService.createOrder(testUser, new Date(), testProducts);

        orderService.addProduct(order, Collections.singletonList(productService.createProduct("Product 3", "desc", "sku3", 10L)));

        assertEquals(3, order.getProducts().size());
        assertEquals(3, orderService.getOrder(order.getId()).getProducts().size());
    }

    @Test
    void getOrder() {
        Order order = orderService.createOrder(testUser, new Date(), testProducts);

        Order retrievedOrder = orderService.getOrder(order.getId());
        assertEquals(order.getId(), retrievedOrder.getId());
    }

    @Test
    void getOrders() {
        Order order1 = orderService.createOrder(testUser, new Date(), testProducts);
        Order order2 = orderService.createOrder(testUser, new Date(), testProducts);

        Iterable<Order> orders = orderService.getOrders(testUser.getId());
        assertTrue(orders.iterator().hasNext());
    }

    @Test
    void deleteOrder() {
        Order order = orderService.createOrder(testUser, new Date(), testProducts);

        orderService.deleteOrder(order);
        assertFalse(testUser.getOrders().contains(order));
    }
}