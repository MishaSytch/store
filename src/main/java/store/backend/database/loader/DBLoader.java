package store.backend.database.loader;

import org.hibernate.Hibernate;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.stereotype.Component;
import store.backend.database.entity.*;
import store.backend.database.repository.*;
import store.backend.security.role.Role;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.*;

@Component
public class DBLoader {
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;
    private ImageRepository imageRepository;
    private OrderRepository orderRepository;
    private PriceRepository priceRepository;
    private ProductRepository productRepository;

    private final Random random = new Random();

    public DBLoader(CategoryRepository categoryRepository, UserRepository userRepository, ImageRepository imageRepository, OrderRepository orderRepository, PriceRepository priceRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.orderRepository = orderRepository;
        this.priceRepository = priceRepository;
        this.productRepository = productRepository;

        loadUsers();
        loadCategories();
        loadImages();
        loadPrices();
        loadOrders();
    }

    private void loadUsers() {
        userRepository.saveAll(
                Arrays.asList(
                    User.builder()
                            .firstName("Misha")
                            .lastName("Sytch")
                            .password("MishaSytchPass")
                            .email("mishaSytch@mail.ru")
                            .role(Role.ADMIN)
                            .build(),
                        User.builder()
                                .firstName("Lena")
                                .lastName("Nam")
                                .password("LenaNamPass")
                                .email("lenaNam@mail.ru")
                                .role(Role.CUSTOMER)
                                .build()
                )
        );
    }

    @Transactional
    private void loadOrders() {
        List<User> users = userRepository.findAll();
        List<Product> products = productRepository.findAll();

        for (User user : users) {
            Order order = Order.builder()
                    .user(user)
                    .date(new Date())
                    .build();

            Hibernate.initialize(order.getProducts());
            for (Product product : products) {
                order.addProduct(product);
            }

            orderRepository.save(order);
        }
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
                                                                                    .SKU(String.valueOf(random.nextGaussian()))
                                                                                    .quantity(100L)
                                                                                    .build(),
                                                                            Product.builder()
                                                                                    .name("Смартфон Apple iPhone 13")
                                                                                    .description("Смартфон Apple iPhone 13 2022 года выпуска")
                                                                                    .SKU(String.valueOf(random.nextGaussian()))
                                                                                    .quantity(100L)
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
                                                                                    .SKU(String.valueOf(random.nextGaussian()))
                                                                                    .quantity(100L)
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
                                                                                                                        .SKU(String.valueOf(random.nextGaussian()))
                                                                                                                        .quantity(100L)
                                                                                                                        .build()
                                                                                                        )
                                                                                                )
                                                                                        )
                                                                                        .build(),
                                                                                Category.builder()
                                                                                        .name("Чехлы")
                                                                                        .products(
                                                                                                new HashSet<>(
                                                                                                    Collections.singletonList(
                                                                                                            Product.builder()
                                                                                                                    .name("Чехол для Huawei P50")
                                                                                                                    .description("Чехол для Huawei P50 для телефона Huawei P50")
                                                                                                                    .SKU(String.valueOf(random.nextGaussian()))
                                                                                                                    .quantity(100L)
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
                                                                    .SKU(String.valueOf(random.nextGaussian()))
                                                                    .quantity(100L)
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
                                                                                        .SKU(String.valueOf(random.nextGaussian()))
                                                                                        .quantity(100L)
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
                                                                                        .SKU(String.valueOf(random.nextGaussian()))
                                                                                        .quantity(100L)
                                                                                        .build()
                                                                        )
                                                                )
                                                        )
                                                        .build()
                                        )
                                )
                        ).build()
                )
        );
    }

    @Transactional
    private void loadImages() {
//        Samsung
        Product samsung = productRepository.findByName("Смартфон Samsung Galaxy S8").get();
        Iterable<Image> images = Arrays.asList(
                Image.builder()
                        .name("https://phone-nsk.ru/upload/resize_cache/iblock/b6e/340_340_140cd750bba9870f18aada2478b24840a/b6ed8493ec6997a1a9caba1060294385.jpg")
                        .reference("https://phone-nsk.ru/upload/resize_cache/iblock/b6e/340_340_140cd750bba9870f18aada2478b24840a/b6ed8493ec6997a1a9caba1060294385.jpg")
                        .build(),
                Image.builder()
                        .name("https://ae04.alicdn.com/kf/H6a01b1baa4744be5a897d07671ae0765H.jpg")
                        .reference("https://ae04.alicdn.com/kf/H6a01b1baa4744be5a897d07671ae0765H.jpg")
                        .build(),
                Image.builder()
                        .name("https://apple11.ru/10714/samsung-galaxy-s8-64gb-sm-g950fd-midnight-black-.jpg")
                        .reference("https://apple11.ru/10714/samsung-galaxy-s8-64gb-sm-g950fd-midnight-black-.jpg")
                        .build()
                );
        for (Image image : images) samsung.addImage(image);
        imageRepository.saveAll(images);

//        Наушники Apple AirPods Pro
        Iterable<Product> airPodsPro = productRepository.findAllByName("Наушники Apple AirPods Pro");
        images = Arrays.asList(
                Image.builder()
                        .name("https://iphoriya.ru/wp-content/uploads/airpods-pro-2022.jpeg")
                        .reference("https://iphoriya.ru/wp-content/uploads/airpods-pro-2022.jpeg")
                        .build(),
                Image.builder()
                        .name("https://gbstore.ru/pictures/product/middle/11946_middle.jpg")
                        .reference("https://gbstore.ru/pictures/product/middle/11946_middle.jpg")
                        .build(),
                Image.builder()
                        .name("https://i-shop.ru/upload/iblock/dd6/dd6b450b68a97d04b1d6dcee58e0c39c.jpeg")
                        .reference("https://i-shop.ru/upload/iblock/dd6/dd6b450b68a97d04b1d6dcee58e0c39c.jpeg")
                        .build()
        );
        for (Product product : airPodsPro) {
            for (Image image : images) {
                product.addImage(image);
            }
        }
        imageRepository.saveAll(images);

//        Чехол для Huawei P50
        Product add2Huawei = productRepository.findByName("Чехол для Huawei P50").get();
        images = Arrays.asList(
                Image.builder()
                        .name("https://ir.ozone.ru/s3/multimedia-8/c1000/6151318772.jpg")
                        .reference("https://ir.ozone.ru/s3/multimedia-8/c1000/6151318772.jpg")
                        .build(),
                Image.builder()
                        .name("https://100gadgets.ru/image/cache/data/newscript5/610117-500x500.jpg")
                        .reference("https://100gadgets.ru/image/cache/data/newscript5/610117-500x500.jpg")
                        .build(),
                Image.builder()
                        .name("https://img.joomcdn.net/75db71619bdf76963be8ec6fc08a37ca88dfbd83_original.jpeg")
                        .reference("https://img.joomcdn.net/75db71619bdf76963be8ec6fc08a37ca88dfbd83_original.jpeg")
                        .build()
        );
        for (Image image : images) add2Huawei.addImage(image);
        imageRepository.saveAll(images);

//        Смартфон Apple iPhone 14
        Product iPhone14 = productRepository.findByName("Смартфон Apple iPhone 14").get();
        images = Arrays.asList(
                Image.builder()
                        .name("https://istudio-shop.ru/a/istudio/files/multifile/2353/4_53_0_0.webp")
                        .reference("https://istudio-shop.ru/a/istudio/files/multifile/2353/4_53_0_0.webp")
                        .build(),
                Image.builder()
                        .name("https://twigo.ru/center/resize_cache/iblock/74a/970_970_1/d8bd0dd74bc298feec877c076eaff574.jpeg")
                        .reference("https://twigo.ru/center/resize_cache/iblock/74a/970_970_1/d8bd0dd74bc298feec877c076eaff574.jpeg")
                        .build(),
                Image.builder()
                        .name("https://mtscdn.ru/upload/iblock/30b/1.png")
                        .reference("https://mtscdn.ru/upload/iblock/30b/1.png")
                        .build()
        );
        for (Image image : images) iPhone14.addImage(image);
        imageRepository.saveAll(images);

//        Смартфон Apple iPhone 13
        Product iPhone13 = productRepository.findByName("Смартфон Apple iPhone 13").get();
        images = Arrays.asList(
                Image.builder()
                        .name("https://storage.yandexcloud.net/itech-media/images/products/2022/9/786de063158e11ecb2c03cecef20832b_465c3607159b11ecb2c03cecef20832b.png")
                        .reference("https://storage.yandexcloud.net/itech-media/images/products/2022/9/786de063158e11ecb2c03cecef20832b_465c3607159b11ecb2c03cecef20832b.png")
                        .build(),
                Image.builder()
                        .name("https://ineed-apple.ru/upload/iblock/a6e/a6e750715da67d4c81603ce356bc65a4.jpg")
                        .reference("https://ineed-apple.ru/upload/iblock/a6e/a6e750715da67d4c81603ce356bc65a4.jpg")
                        .build(),
                Image.builder()
                        .name("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_OPMIfa4MokQpVu48pqPN_ShhbMG6iIXdUg&s")
                        .reference("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS_OPMIfa4MokQpVu48pqPN_ShhbMG6iIXdUg&s")
                        .build()
        );
        for (Image image : images) iPhone13.addImage(image);
        imageRepository.saveAll(images);

//        Смартфон Huawei P50
        Product huaweiP50 = productRepository.findByName("Смартфон Huawei P50").get();
        images = Arrays.asList(
                Image.builder()
                        .name("https://main-cdn.sbermegamarket.ru/big1/hlr-system/-51/676/228/111/712/51/100036465857b0.jpg")
                        .reference("https://main-cdn.sbermegamarket.ru/big1/hlr-system/-51/676/228/111/712/51/100036465857b0.jpg")
                        .build(),
                Image.builder()
                        .name("https://main-cdn.sbermegamarket.ru/big1/hlr-system/-34/787/868/128/151/4/100030319610b0.jpg")
                        .reference("https://main-cdn.sbermegamarket.ru/big1/hlr-system/-34/787/868/128/151/4/100030319610b0.jpg")
                        .build(),
                Image.builder()
                        .name("https://shop-cdn.huawei.com/ru/pms/uomcdn/RUHW/pms/202202/gbom/6941487244423/428_428_A378AA4E3054ABBDC666E9B2135C75B2mp.png")
                        .reference("https://shop-cdn.huawei.com/ru/pms/uomcdn/RUHW/pms/202202/gbom/6941487244423/428_428_A378AA4E3054ABBDC666E9B2135C75B2mp.png")
                        .build()
        );
        for (Image image : images) huaweiP50.addImage(image);
        imageRepository.saveAll(images);

//        Умная колонка Яндекс Станция
        Product yandex = productRepository.findByName("Умная колонка Яндекс Станция").get();
        images = Arrays.asList(
                Image.builder()
                        .name("https://main-cdn.sbermegamarket.ru/big1/hlr-system/-33/634/602/512/281/325/100030020889b2.jpg")
                        .reference("https://main-cdn.sbermegamarket.ru/big1/hlr-system/-33/634/602/512/281/325/100030020889b2.jpg")
                        .build(),
                Image.builder()
                        .name("https://doctorhead.ru/upload/resize_cache/webp/iblock/01e/j2vnilfej591ocf7khsbgibu7jz30w91/yandex._station_mini_plus_gr_2_1.webp")
                        .reference("https://doctorhead.ru/upload/resize_cache/webp/iblock/01e/j2vnilfej591ocf7khsbgibu7jz30w91/yandex._station_mini_plus_gr_2_1.webp")
                        .build(),
                Image.builder()
                        .name("https://main-cdn.sbermegamarket.ru/big1/hlr-system/183/806/901/210/272/028/100029532466b0.jpg")
                        .reference("https://main-cdn.sbermegamarket.ru/big1/hlr-system/183/806/901/210/272/028/100029532466b0.jpg")
                        .build()
        );
        for (Image image : images) yandex.addImage(image);
        imageRepository.saveAll(images);
    }

    @Transactional
    private void loadPrices() {
        Iterable<Product> products = productRepository.findAll();

        for (Product product : products) {
            Price price = Price.builder()
                    .price(BigDecimal.valueOf(random.nextDouble() * 100))
                    .date(new Date())
                    .build();

            product.addPrice(price);
            priceRepository.save(price);
        }
    }
}
