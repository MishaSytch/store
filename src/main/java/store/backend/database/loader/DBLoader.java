package store.backend.database.loader;

import org.springframework.stereotype.Component;
import store.backend.security.dto.SignUpRequest;
import store.backend.security.role.Role;
import store.backend.database.entity.Category;
import store.backend.database.entity.Product;
import store.backend.security.service.AuthenticationService;
import store.backend.security.service.UserService;
import store.backend.service.product.CategoryService;
import store.backend.service.product.ProductService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

@Component
public class DBLoader {
    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;
    private final AuthenticationService authenticationService;
    private final Random random = new Random();

    public DBLoader(CategoryService categoryService, ProductService productService, UserService userService, AuthenticationService authenticationService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.userService = userService;
        this.authenticationService = authenticationService;

        if (this.productService.getAllProducts().isEmpty()) {
            loadItems();
        }
        if (this.userService.getUsers().isEmpty()) {
            loadUsers();
        }
    }

    private void loadUsers() {
        authenticationService.signUp(
                SignUpRequest.builder()
                        .firstName("admin")
                        .lastName("admin")
                        .password("password_admin")
                        .email("admin@mail.ru")
                        .build(),
                Role.ADMIN);
    }


    private void loadItems() {
        // Создание основной категории "Смартфоны"
        Category smartPhones = categoryService.createCategory("Смартфоны");

        // Создание подкатегорий для "Смартфоны"
        Category apple = categoryService.createCategory("Apple");
        Category samsung = categoryService.createCategory("Samsung");
        Category addition = categoryService.createCategory("Сопутствующие товары");

        // Добавление подкатегорий в основную категорию
        smartPhones.addCategory(apple);
        smartPhones.addCategory(samsung);
        smartPhones.addCategory(addition);

        // Создание продуктов и добавление их в подкатегории
        Product iphone14 = productService.createProduct(
                "Смартфон Apple iPhone 14",
                "Смартфон Apple iPhone 14 2023 года выпуска",
                String.valueOf(random.nextGaussian()),
                100L
        );
        iphone14.addPrice(productService.createPrice(BigDecimal.valueOf(random.nextDouble() * 100), new Date()));
        iphone14.addImage(productService.createImage("https://istudio-shop.ru/a/istudio/files/multifile/2353/4_53_0_0.webp", "https://istudio-shop.ru/a/istudio/files/multifile/2353/4_53_0_0.webp"));
        iphone14.addImage(productService.createImage("https://twigo.ru/center/resize_cache/iblock/74a/970_970_1/d8bd0dd74bc298feec877c076eaff574.jpeg", "https://twigo.ru/center/resize_cache/iblock/74a/970_970_1/d8bd0dd74bc298feec877c076eaff574.jpeg"));
        iphone14.addImage(productService.createImage("https://mtscdn.ru/upload/iblock/30b/1.png", "https://mtscdn.ru/upload/iblock/30b/1.png"));
        apple.addProduct(iphone14);
        productService.saveProduct(iphone14);

        Product iphone13 = productService.createProduct(
                "Смартфон Apple iPhone 13",
                "Смартфон Apple iPhone 13 2022 года выпуска",
                String.valueOf(random.nextGaussian()),
                100L
        );
        iphone13.addPrice(productService.createPrice(BigDecimal.valueOf(random.nextDouble() * 100), new Date()));
        iphone13.addImage(productService.createImage("https://storage.yandexcloud.net/itech-media/images/products/2022/9/786de063158e11ecb2c03cecef20832b_465c3607159b11ecb2c03cecef20832b.png", "https://storage.yandexcloud.net/itech-media/images/products/2022/9/786de063158e11ecb2c03cecef20832b_465c3607159b11ecb2c03cecef20832b.png"));
        iphone13.addImage(productService.createImage("https://ineed-apple.ru/upload/iblock/a6e/a6e750715da67d4c81603ce356bc65a4.jpg", "https://ineed-apple.ru/upload/iblock/a6e/a6e750715da67d4c81603ce356bc65a4.jpg"));
        iphone13.addImage(productService.createImage("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_OPMIfa4MokQpVu48pqPN_ShhbMG6iIXdUg&s", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_OPMIfa4MokQpVu48pqPN_ShhbMG6iIXdUg&s"));
        apple.addProduct(iphone13);
        productService.saveProduct(iphone13);

        Product product_samsung = productService.createProduct(
                "Samsung Galaxy S8",
                "Смартфон Samsung Galaxy S8",
                String.valueOf(random.nextGaussian()),
                100L
        );
        product_samsung.addPrice(productService.createPrice(BigDecimal.valueOf(random.nextDouble() * 100), new Date()));
        product_samsung.addImage(productService.createImage("https://phone-nsk.ru/upload/resize_cache/iblock/b6e/340_340_140cd750bba9870f18aada2478b24840a/b6ed8493ec6997a1a9caba1060294385.jpg", "https://phone-nsk.ru/upload/resize_cache/iblock/b6e/340_340_140cd750bba9870f18aada2478b24840a/b6ed8493ec6997a1a9caba1060294385.jpg"));
        product_samsung.addImage(productService.createImage("https://ae04.alicdn.com/kf/H6a01b1baa4744be5a897d07671ae0765H.jpg", "https://ae04.alicdn.com/kf/H6a01b1baa4744be5a897d07671ae0765H.jpg"));
        product_samsung.addImage(productService.createImage("https://apple11.ru/10714/samsung-galaxy-s8-64gb-sm-g950fd-midnight-black-.jpg", "https://apple11.ru/10714/samsung-galaxy-s8-64gb-sm-g950fd-midnight-black-.jpg"));
        samsung.addProduct(product_samsung);
        productService.saveProduct(product_samsung);

        Category additionHeadphones = categoryService.createCategory("Наушники");
        Product appleHeadphones = productService.createProduct(
                "Наушники Apple AirPods Pro",
                "Наушники Apple AirPods Pro для продукции Apple",
                String.valueOf(random.nextGaussian()),
                100L
        );
        appleHeadphones.addPrice(productService.createPrice(BigDecimal.valueOf(random.nextDouble() * 100), new Date()));
        appleHeadphones.addImage(productService.createImage("https://iphoriya.ru/wp-content/uploads/airpods-pro-2022.jpeg", "https://iphoriya.ru/wp-content/uploads/airpods-pro-2022.jpeg"));
        appleHeadphones.addImage(productService.createImage("https://gbstore.ru/pictures/product/middle/11946_middle.jpg", "https://gbstore.ru/pictures/product/middle/11946_middle.jpg"));
        appleHeadphones.addImage(productService.createImage("https://i-shop.ru/upload/iblock/dd6/dd6b450b68a97d04b1d6dcee58e0c39c.jpeg", "https://i-shop.ru/upload/iblock/dd6/dd6b450b68a97d04b1d6dcee58e0c39c.jpeg"));
        additionHeadphones.addProduct(appleHeadphones);
        addition.addCategory(additionHeadphones);

        Category pouch = categoryService.createCategory("Чехлы");
        Product product_pouch = productService.createProduct(
                "Чехол для Huawei P50",
                "Чехол для Huawei P50 для телефона Huawei P50",
                String.valueOf(random.nextGaussian()),
                100L
        );
        product_pouch.addImage(productService.createImage("https://ir.ozone.ru/s3/multimedia-8/c1000/6151318772.jpg", "https://ir.ozone.ru/s3/multimedia-8/c1000/6151318772.jpg"));
        product_pouch.addImage(productService.createImage("https://100gadgets.ru/image/cache/data/newscript5/610117-500x500.jpg", "https://100gadgets.ru/image/cache/data/newscript5/610117-500x500.jpg"));
        product_pouch.addImage(productService.createImage("https://img.joomcdn.net/75db71619bdf76963be8ec6fc08a37ca88dfbd83_original.jpeg", "https://img.joomcdn.net/75db71619bdf76963be8ec6fc08a37ca88dfbd83_original.jpeg"));
        product_pouch.addPrice(productService.createPrice(BigDecimal.valueOf(random.nextDouble() * 100), new Date()));
        pouch.addProduct(product_pouch);
        productService.saveProduct(product_pouch);
        addition.addCategory(pouch);

        Product huaweiP50 = productService.createProduct(
                "Смартфон Huawei P50",
                "Смартфон Huawei P50 из китая",
                String.valueOf(random.nextGaussian()),
                100L
        );
        huaweiP50.addImage(productService.createImage("https://main-cdn.sbermegamarket.ru/big1/hlr-system/-51/676/228/111/712/51/100036465857b0.jpg", "https://main-cdn.sbermegamarket.ru/big1/hlr-system/-51/676/228/111/712/51/100036465857b0.jpg"));
        huaweiP50.addImage(productService.createImage("https://main-cdn.sbermegamarket.ru/big1/hlr-system/-34/787/868/128/151/4/100030319610b0.jpg", "https://main-cdn.sbermegamarket.ru/big1/hlr-system/-34/787/868/128/151/4/100030319610b0.jpg"));
        huaweiP50.addImage(productService.createImage("https://shop-cdn.huawei.com/ru/pms/uomcdn/RUHW/pms/202202/gbom/6941487244423/428_428_A378AA4E3054ABBDC666E9B2135C75B2mp.png", "https://shop-cdn.huawei.com/ru/pms/uomcdn/RUHW/pms/202202/gbom/6941487244423/428_428_A378AA4E3054ABBDC666E9B2135C75B2mp.png"));
        huaweiP50.addPrice(productService.createPrice(BigDecimal.valueOf(random.nextDouble() * 100), new Date()));
        smartPhones.addProduct(huaweiP50);
        productService.saveProduct(huaweiP50);

        Category audio = categoryService.createCategory("Аудиотехника");
        Category locAudio = categoryService.createCategory("Портативные колонки");
        Category headPhones = categoryService.createCategory("Наушники");
        headPhones.addProduct(appleHeadphones);
        audio.addCategory(headPhones);
        Product yandex = productService.createProduct(
                "Умная колонка Яндекс Станция",
                "Умная колонка Яндекс Станция <<Алиса>>",
                String.valueOf(random.nextGaussian()),
                100L
        );
        yandex.addImage(productService.createImage("https://main-cdn.sbermegamarket.ru/big1/hlr-system/-33/634/602/512/281/325/100030020889b2.jpg", "https://main-cdn.sbermegamarket.ru/big1/hlr-system/-33/634/602/512/281/325/100030020889b2.jpg"));
        yandex.addImage(productService.createImage("https://doctorhead.ru/upload/resize_cache/webp/iblock/01e/j2vnilfej591ocf7khsbgibu7jz30w91/yandex._station_mini_plus_gr_2_1.webp", "https://doctorhead.ru/upload/resize_cache/webp/iblock/01e/j2vnilfej591ocf7khsbgibu7jz30w91/yandex._station_mini_plus_gr_2_1.webp"));
        yandex.addImage(productService.createImage("https://main-cdn.sbermegamarket.ru/big1/hlr-system/183/806/901/210/272/028/100029532466b0.jpg", "https://main-cdn.sbermegamarket.ru/big1/hlr-system/183/806/901/210/272/028/100029532466b0.jpg"));
        yandex.addPrice(productService.createPrice(BigDecimal.valueOf(random.nextDouble() * 100), new Date()));
        locAudio.addProduct(yandex);
        productService.saveProduct(yandex);
        audio.addCategory(locAudio);

        categoryService.saveCategory(headPhones);
        categoryService.saveCategory(locAudio);
        categoryService.saveCategory(audio);
        categoryService.saveCategory(pouch);
        categoryService.saveCategory(additionHeadphones);
        categoryService.saveCategory(addition);
        categoryService.saveCategory(samsung);
        categoryService.saveCategory(apple);
        categoryService.saveCategory(smartPhones);
    }
}
