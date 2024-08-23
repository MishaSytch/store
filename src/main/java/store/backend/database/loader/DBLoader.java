package store.backend.database.loader;

import org.springframework.stereotype.Component;
import store.backend.database.entity.Category;
import store.backend.database.entity.Product;
import store.backend.security.service.CustomerService;
import store.backend.service.product.CategoryService;
import store.backend.service.product.ProductService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

@Component
public class DBLoader {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final Random random = new Random();

    public DBLoader(CategoryService categoryService, ProductService productService, CustomerService customerService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.customerService = customerService;

        load();
    }

    public void load() {
        loadItems();
    }

    private void loadItems() {
        // Создание основной категории "Смартфоны"
        Category smartPhones = categoryService.createCategory("Смартфоны");

        // Создание подкатегорий для "Смартфоны"
        Category apple = categoryService.createCategory("Apple");
        Category samsung = categoryService.createCategory("Samsung");
        Category addition = categoryService.createCategory("Сопутствующие товары");

        // Добавление подкатегорий в основную категорию
        categoryService.addCategory(smartPhones.getId(), apple);
        categoryService.addCategory(smartPhones.getId(), samsung);
        categoryService.addCategory(smartPhones.getId(), addition);

        // Создание продуктов и добавление их в подкатегории
        Product iphone14 = productService.createProduct(
                "Смартфон Apple iPhone 14",
                "Смартфон Apple iPhone 14 2023 года выпуска",
                String.valueOf(random.nextGaussian()),
                100L
        );
        productService.addPrice(iphone14.getId(), productService.createPrice(BigDecimal.valueOf(random.nextDouble() * 100), new Date()));
        productService.addImage(iphone14.getId(), productService.createImage("https://istudio-shop.ru/a/istudio/files/multifile/2353/4_53_0_0.webp", "https://istudio-shop.ru/a/istudio/files/multifile/2353/4_53_0_0.webp"));
        productService.addImage(iphone14.getId(), productService.createImage("https://twigo.ru/center/resize_cache/iblock/74a/970_970_1/d8bd0dd74bc298feec877c076eaff574.jpeg", "https://twigo.ru/center/resize_cache/iblock/74a/970_970_1/d8bd0dd74bc298feec877c076eaff574.jpeg"));
        productService.addImage(iphone14.getId(), productService.createImage("https://mtscdn.ru/upload/iblock/30b/1.png", "https://mtscdn.ru/upload/iblock/30b/1.png"));
        categoryService.addProduct(apple.getId(), iphone14);

        Product product_samsung = productService.createProduct(
                "Samsung Galaxy S8",
                "Смартфон Samsung Galaxy S8",
                String.valueOf(random.nextGaussian()),
                100L
        );
        productService.addPrice(product_samsung.getId(), productService.createPrice(BigDecimal.valueOf(random.nextDouble() * 100), new Date()));
        productService.addImage(product_samsung.getId(), productService.createImage("https://phone-nsk.ru/upload/resize_cache/iblock/b6e/340_340_140cd750bba9870f18aada2478b24840a/b6ed8493ec6997a1a9caba1060294385.jpg", "https://phone-nsk.ru/upload/resize_cache/iblock/b6e/340_340_140cd750bba9870f18aada2478b24840a/b6ed8493ec6997a1a9caba1060294385.jpg"));
        productService.addImage(product_samsung.getId(), productService.createImage("https://ae04.alicdn.com/kf/H6a01b1baa4744be5a897d07671ae0765H.jpg", "https://ae04.alicdn.com/kf/H6a01b1baa4744be5a897d07671ae0765H.jpg"));
        productService.addImage(product_samsung.getId(), productService.createImage("https://apple11.ru/10714/samsung-galaxy-s8-64gb-sm-g950fd-midnight-black-.jpg", "https://apple11.ru/10714/samsung-galaxy-s8-64gb-sm-g950fd-midnight-black-.jpg"));
        categoryService.addProduct(samsung.getId(), product_samsung);

        Category additionHeadphones = categoryService.createCategory("Наушники");
        Product appleHeadphones = productService.createProduct(
                "Наушники Apple AirPods Pro",
                "Наушники Apple AirPods Pro для продукции Apple",
                String.valueOf(random.nextGaussian()),
                100L
        );
        productService.addPrice(appleHeadphones.getId(), productService.createPrice(BigDecimal.valueOf(random.nextDouble() * 100), new Date()));
        productService.addImage(appleHeadphones.getId(), productService.createImage("https://iphoriya.ru/wp-content/uploads/airpods-pro-2022.jpeg", "https://iphoriya.ru/wp-content/uploads/airpods-pro-2022.jpeg"));
        productService.addImage(appleHeadphones.getId(), productService.createImage("https://gbstore.ru/pictures/product/middle/11946_middle.jpg", "https://gbstore.ru/pictures/product/middle/11946_middle.jpg"));
        productService.addImage(appleHeadphones.getId(), productService.createImage("https://i-shop.ru/upload/iblock/dd6/dd6b450b68a97d04b1d6dcee58e0c39c.jpeg", "https://i-shop.ru/upload/iblock/dd6/dd6b450b68a97d04b1d6dcee58e0c39c.jpeg"));
        categoryService.addProduct(additionHeadphones.getId(), appleHeadphones);
        categoryService.addCategory(addition.getId(), additionHeadphones);

        Category pouch = categoryService.createCategory("Чехлы");
        Product product_pouch = productService.createProduct(
                "Чехол для Huawei P50",
                "Чехол для Huawei P50 для телефона Huawei P50",
                String.valueOf(random.nextGaussian()),
                100L
        );
        productService.addPrice(product_pouch.getId(), productService.createPrice(BigDecimal.valueOf(random.nextDouble() * 100), new Date()));
        categoryService.addProduct(pouch.getId(), product_pouch);
    }
}