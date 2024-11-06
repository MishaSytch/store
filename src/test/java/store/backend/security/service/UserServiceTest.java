package store.backend.security.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.backend.database.entity.Product;
import store.backend.database.entity.User;
import store.backend.database.entity.Order;
import store.backend.database.loader.DBLoader;
import store.backend.security.dto.SignUpRequest;
import store.backend.security.role.Role;
import store.backend.service.product.ProductService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private DBLoader dbLoader;

    @Autowired
    private ProductService productService;

    private User testUser;
    private List<Product> testProducts;

    @BeforeEach
    void setUp() {
        dbLoader.delete();

        SignUpRequest request = new SignUpRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("misha.sytch@mail.ru");
        request.setPassword("password");

        Role role = Role.CUSTOMER;
        authenticationService.signUp(request, role);

        testUser = userService.getByEmail(request.getEmail());

        Product product1 = productService.createProduct("Product 1", "desc", "sku1", 10L);
        productService.addPrice(product1, productService.createPrice(new BigDecimal(10L), new Date()));

        Product product2 = productService.createProduct("Product 2", "desc", "sku2", 10L);
        productService.addPrice(product2, productService.createPrice(new BigDecimal(10L), new Date()));

        testProducts = new ArrayList<>();
        testProducts.add(product1);
        testProducts.add(product2);
    }

    @Test
    void testGetUser() {
        Optional<User> user = userService.getUser(testUser.getId());
        assertTrue(user.isPresent());
        assertEquals(testUser.getEmail(), user.get().getEmail());
    }

    @Test
    void testGetUsers() {
        List<User> users = userService.getUsers();
        assertFalse(users.isEmpty());
    }

    @Test
    void testUpdateUser() {
        testUser.setFirstName("UpdatedName");
        User updatedUser = userService.updateUser(testUser);
        assertEquals("UpdatedName", updatedUser.getFirstName());
        assertEquals("UpdatedName", userService.getUser(updatedUser.getId()).get().getFirstName());
    }

//    @Test
    void testAddOrder() {
        Order order = orderService.createOrder(testUser, new Date(), testProducts);
        Order savedOrder = userService.addOrder(testUser, order);
        assertNotNull(savedOrder);
        assertTrue(testUser.getOrders().contains(savedOrder));
    }

//    @Test
    void testDeleteOrder() {
        Order order = orderService.createOrder(testUser, new Date(), testProducts);
        userService.addOrder(testUser, order);
        userService.deleteOrder(testUser, order.getId());
        assertFalse(testUser.getOrders().contains(order));
    }

    @Test
    void testDeleteUser() {
        userService.deleteUser(testUser.getId());
        Optional<User> deletedUser = userService.getUser(testUser.getId());
        assertFalse(deletedUser.isPresent());
    }

    @Test
    void testCreateUser() {
        User newUser = User.builder()
                .firstName("New")
                .lastName("User")
                .email("newuser@example.com")
                .password("newpassword")
                .build();
        User createdUser = userService.create(newUser);
        assertNotNull(createdUser);
        assertEquals("newuser@example.com", createdUser.getEmail());
    }

    @Test
    void testGetByEmail() {
        User user = userService.getByEmail(testUser.getEmail());
        assertEquals(testUser.getId(), user.getId());
    }
}