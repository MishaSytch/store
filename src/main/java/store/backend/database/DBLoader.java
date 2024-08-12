package store.backend.database;

import org.springframework.stereotype.Component;
import store.backend.database.entity.*;
import store.backend.database.repository.*;

import java.util.*;

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


    }

    private void LoadCustomers() {
        customerRepository.saveAll(Arrays.asList(
                new Customer(
                    "Misha", "Sytch", "MishaSytchPass", "mishaSytch@mail.ru"
                ),
                new Customer(
                        "Lena", "Nam", "LenaNamPass", "lenaNam@mail.ru"
                )
        )
        );
    }

    private void LoadCategories() {
        categoryRepository.saveAll(
                Arrays.asList(
                    new Category("Смартфоны",
                            new HashSet<>(
                                    Arrays.asList(
                                        new Category("Apple",
                                                Arrays.asList(
                                                     new Product(
                                                             "Смартфон Apple iPhone 14",
                                                             "Смартфон Apple iPhone 14 2023 года выпуска"
                                                     ),
                                                        new Product(
                                                                "Смартфон Apple iPhone 13",
                                                                "Смартфон Apple iPhone 13 2022 года выпуска"
                                                        )
                                                )
                                        ),
                                        new Category("Samsung",
                                                Collections.singletonList(
                                                        new Product(
                                                                "Смартфон Samsung Galaxy S8",
                                                                "Смартфон Samsung Galaxy S8 2023 года выпуска"
                                                        )
                                                )
                                        ),
                                        new Category("Сопутствующие товары",
                                                new HashSet<>(
                                                        Arrays.asList(
                                                            new Category("Наушники",
                                                                    Collections.singletonList(
                                                                            new Product(
                                                                                    "Наушники Apple AirPods Pro",
                                                                                    "Наушники Apple AirPods Pro для продукции Apple"
                                                                            )
                                                                    )
                                                            ),
                                                            new Category("Чехлы",
                                                                    Collections.singletonList(
                                                                            new Product(
                                                                                    "Чехол для Huawei P50",
                                                                                    "Чехол для Huawei P50 для телефона Huawei P50"
                                                                            )
                                                                    )
                                                            )
                                                    )
                                                )
                                        )
                                    )
                            ),
                            Collections.singletonList(
                                    new Product(
                                            "Смартфон Huawei P50",
                                            "Смартфон Huawei P50 телефон без категории"
                                    )
                            )
                    ),
                    new Category("Аудиотехника",
                            new HashSet<>(
                                    Arrays.asList(
                                        new Category("Портативные колонки",
                                                Collections.singletonList(
                                                        new Product(
                                                                "Умная колонка Яндекс Станция",
                                                                "Умная колонка Яндекс Станция"
                                                        )
                                                )
                                        ),
                                        new Category("Наушники",
                                                Collections.singletonList(
                                                        new Product(
                                                                "Наушники Apple AirPods Pro",
                                                                "Наушники Apple AirPods Pro для продукции Apple"
                                                        )
                                                )
                                        )
                                    )
                            )
                    )
                )
        );
    }
}
