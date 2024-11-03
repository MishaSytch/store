package store.backend.service.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import store.backend.database.entity.Price;
import store.backend.database.entity.Product;
import store.backend.database.loader.DBLoader;
import store.backend.database.repository.PriceRepository;
import store.backend.database.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootTest
class PriceServiceTest {

    private final BigDecimal price = new BigDecimal(10);

    @Autowired
    private PriceService priceService;

    @Autowired
    private DBLoader dbLoader;

    @BeforeEach
    void start() {
        dbLoader.delete();
        dbLoader.load();
    }

    @Test
    void createPrice() {
        priceService.createPrice(price, new Date());
        }

    @Test
    void savePrice() {
        BigDecimal newPrice = price.add(new BigDecimal(10));
        Price price_1 = Price.builder()
                .price(newPrice)
                .date(new Date())
                .build();
        priceService.savePrice(price_1);
    }


    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PriceRepository priceRepository;

    @Test
    void updatePrice() {
        BigDecimal initialPrice = price.add(new BigDecimal(5));
        BigDecimal updatedPrice = price.add(new BigDecimal(15));

        Price priceEntity = priceService.createPrice(initialPrice, new Date());

        priceEntity.setPrice(updatedPrice);
        priceService.updatePrice(priceEntity);

        Assertions.assertEquals(updatedPrice, priceService.savePrice(priceEntity).getPrice());
    }

    @Test
    @Transactional
    void deletePrice() {
        BigDecimal priceValue = price.add(new BigDecimal(20));

        Price priceEntity = priceService.createPrice(priceValue, new Date());

        Product product = productService.createProduct("Product", "Desc", "sku", 1L);
        product.addPrice(priceEntity);
        productRepository.save(product);

        priceService.deletePrice(priceEntity.getId());

        Assertions.assertFalse(priceRepository.findById(priceEntity.getId()).isPresent());
    }
}