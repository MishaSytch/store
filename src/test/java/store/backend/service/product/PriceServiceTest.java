package store.backend.service.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.backend.database.entity.Price;
import store.backend.database.repository.PriceRepository;

import java.math.BigDecimal;
import java.util.Date;

//@SpringBootTest
class PriceServiceTest {
    @Autowired
    private PriceService priceService;

    private final BigDecimal price = new BigDecimal(10);

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

    @Test
    void updatePrice() {
    }

    @Test
    void deletePrice() {
    }
}