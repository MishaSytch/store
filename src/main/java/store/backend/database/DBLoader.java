package store.backend.database;

import org.springframework.stereotype.Component;
import store.backend.database.entity.*;
import store.backend.database.repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class DBLoader {
    private CategoryRepository categoryRepository;
    private CustomerRepository customerRepository;
    private ImageRepository imageRepository;
    private OrderRepository orderRepository;
    private PriceRepository priceRepository;
    private ProductRepository productRepository;
    private SKURepository skuRepository;

    public DBLoader(CategoryRepository categoryRepository, CustomerRepository customerRepository, ImageRepository imageRepository, OrderRepository orderRepository, PriceRepository priceRepository, ProductRepository productRepository, SKURepository skuRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.imageRepository = imageRepository;
        this.orderRepository = orderRepository;
        this.priceRepository = priceRepository;
        this.productRepository = productRepository;
        this.skuRepository = skuRepository;

        loadCategories();
        loadCustomers();
    }

    private void loadCustomers() {
        customerRepository.saveAll(
                Arrays.asList(
                    Customer.builder()
                            .firstName("Misha")
                            .lastName("Sytch")
                            .password("MishaSytchPass")
                            .email("mishaSytch@mail.ru")
                            .build(),
                        Customer.builder()
                                .firstName("Lena")
                                .lastName("Nam")
                                .password("LenaNamPass")
                                .email("lenaNam@mail.ru")
                                .build()
                )
        );
    }

    private void loadCategories() {
        categoryRepository.saveAll(
                Arrays.asList(
                    Category.builder()
                            .name("Смартфоны")
                            .categories(
                                    new HashSet<>(
                                            Arrays.asList(
                                                Category.builder()
                                                        .name("Apple")
                                                        .products(
                                                                new HashSet<>(
                                                                        Arrays.asList(
                                                                            Product.builder()
                                                                                    .name("Смартфон Apple iPhone 14")
                                                                                    .description("Смартфон Apple iPhone 14 2023 года выпуска")
                                                                                    .build(),
                                                                            Product.builder()
                                                                                    .name("Смартфон Apple iPhone 13")
                                                                                    .description("Смартфон Apple iPhone 13 2022 года выпуска")
                                                                                    .build()
                                                                        )
                                                                )
                                                        ).build(),
                                                Category.builder()
                                                        .name("Samsung")
                                                        .products(
                                                                new HashSet<>(
                                                                        Collections.singletonList(
                                                                            Product.builder()
                                                                                    .name("Смартфон Samsung Galaxy S8")
                                                                                    .description("Смартфон Samsung Galaxy S8 2023 года выпуска")
                                                                                    .build()
                                                                        )
                                                                )
                                                        ).build(),
                                                Category.builder()
                                                        .name("Сопутствующие товары")
                                                        .categories(
                                                                new HashSet<>(
                                                                        Arrays.asList(
                                                                                Category.builder()
                                                                                        .name("Наушники")
                                                                                        .products(
                                                                                                new HashSet<>(
                                                                                                        Collections.singletonList(
                                                                                                                Product.builder()
                                                                                                                        .name("Наушники Apple AirPods Pro")
                                                                                                                        .description("Наушники Apple AirPods Pro для продукции Apple")
                                                                                                                        .build()
                                                                                                        )
                                                                                                )
                                                                                        ).build(),
                                                                                Category.builder()
                                                                                        .name("Чехлы")
                                                                                        .products(
                                                                                                new HashSet<>(
                                                                                                    Collections.singletonList(
                                                                                                            Product.builder()
                                                                                                                    .name("Чехол для Huawei P50")
                                                                                                                    .description("Чехол для Huawei P50 для телефона Huawei P50")
                                                                                                                    .build()
                                                                                                    )
                                                                                                )
                                                                                        ).build()
                                                                        )
                                                                )
                                                        ).build()
                                            )
                                    )
                            ).products(
                                            new HashSet<>(
                                                    Collections.singletonList(
                                                            Product.builder()
                                                                    .name("Смартфон Huawei P50")
                                                                    .description("Смартфон Huawei P50 телефон без категории")
                                                                    .build()
                                                    )
                                            )
                            ).build(),
                Category.builder()
                        .name("Аудиотехника")
                        .categories(
                                new HashSet<>(
                                        Arrays.asList(
                                                Category.builder()
                                                        .name("Портативные колонки")
                                                        .products(
                                                                new HashSet<>(
                                                                        Collections.singletonList(
                                                                                Product.builder()
                                                                                        .name("Умная колонка Яндекс Станция")
                                                                                        .description("Умная колонка Яндекс Станция")
                                                                                        .build()
                                                                        )
                                                                )
                                                        ).build(),
                                                Category.builder()
                                                        .name("Наушники")
                                                        .products(
                                                                new HashSet<>(
                                                                        Collections.singletonList(
                                                                                Product.builder()
                                                                                        .name("Наушники Apple AirPods Pro")
                                                                                        .description("Наушники Apple AirPods Pro для продукции Apple")
                                                                                        .build()
                                                                        )
                                                                )
                                                        ).build()
                                        )
                                )
                        ).build()
                )
        );
    }

    private void loadProducts() {
        productRepository.saveAll(
                Arrays.asList(
                        Product.builder()
                                .name("Наушники Apple AirPods Pro")
                                .description("Наушники Apple AirPods Pro для продукции Apple")
                                .build(),
                        Product.builder()
                                .name("Умная колонка Яндекс Станция")
                                .description("Умная колонка Яндекс Станция")
                                .build()
                )
        );
    }

}
