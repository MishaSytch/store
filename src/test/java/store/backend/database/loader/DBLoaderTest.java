package store.backend.database.loader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import store.backend.database.entity.Category;
import store.backend.database.entity.Product;
import store.backend.database.entity.User;
import store.backend.service.product.CategoryService;
import store.backend.service.product.ProductService;
import store.backend.security.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DBLoaderTest {

    @Autowired
    private DBLoader dbLoader;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Test
    @Transactional
    public void testLoad() {
        // Вызов метода загрузки данных
        dbLoader.load();

        // Проверка загруженных категорий
        List<Category> categories = categoryService.getCategories();
        assertFalse(categories.isEmpty(), "Categories should be loaded");

        // Проверка загруженных продуктов
        List<Product> products = productService.getAllProducts();
        assertFalse(products.isEmpty(), "Products should be loaded");

        // Проверка загруженных пользователей
        List<User> users = userService.getUsers();
        assertFalse(users.isEmpty(), "Users should be loaded");

        // Дополнительные проверки по имени продукта и категории
        List<Category> smartPhonesCategory = categories.stream()
                .filter(c -> "Смартфоны".equals(c.getName()))
                .peek(c -> {
                    assertEquals(3, (long) c.getCategories().size());
                    assertEquals(1, (long) c.getProducts().size());
                }).collect(Collectors.toList());
        assertNotNull(smartPhonesCategory, "Category 'Смартфоны' should exist");
        assertEquals(1, smartPhonesCategory.size());


        Product iphone14 = products.stream()
                .filter(p -> "Смартфон Apple iPhone 14".equals(p.getName()))
                .peek(p -> {
                    assertEquals(1, (long) p.getCategories().size());
                    assertEquals(3, (long) p.getImages().size());
                    assertEquals(1, (long) p.getPrices().size());
                    assertEquals(100, (long) p.getQuantity());
                })
                .findFirst().get();

        assertNotNull(iphone14, "Product 'Смартфон Apple iPhone 14' should exist");


    }

    @Test
    @Transactional
    public void testDelete() {
        // Загрузка данных перед удалением
        dbLoader.load();

        // Удаление всех данных
        dbLoader.delete();

        // Проверка, что данные удалены
        assertTrue(categoryService.getCategories().isEmpty(), "Categories should be deleted");
        assertTrue(productService.getAllProducts().isEmpty(), "Products should be deleted");
        assertTrue(userService.getUsers().isEmpty(), "Users should be deleted");
    }
}