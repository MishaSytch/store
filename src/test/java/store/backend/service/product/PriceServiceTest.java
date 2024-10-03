package store.backend.service.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import store.backend.database.entity.Price;
import store.backend.database.repository.PriceRepository;

import java.math.BigDecimal;
import java.util.Date;

class PriceServiceTest {
    @Autowired
    private PriceService priceService;
    @Autowired
    private PriceRepository priceRepository;

    private final BigDecimal price = new BigDecimal(10);

    @Test
    void createPrice() {
        Assertions.assertTrue(priceRepository.findAll().stream().noneMatch(p -> p.getPrice().equals(price)));
        priceService.createPrice(price, new Date());
        Assertions.assertEquals(1, priceRepository.findAll().stream().filter(p -> p.getPrice().equals(price)).count());
    }

    @Test
    void savePrice() {
        BigDecimal newPrice = price.add(new BigDecimal(10));
        Assertions.assertTrue(priceRepository.findAll().stream().noneMatch(p -> p.getPrice().equals(newPrice)));
        Price price_1 = Price.builder()
                .price(newPrice)
                .date(new Date())
                .build();
        priceService.savePrice(price_1);
        Assertions.assertEquals(1, priceRepository.findAll().stream().filter(p -> p.getPrice().equals(newPrice)).count());
    }

    @Test
    void updatePrice() {
    }

    @Test
    void deletePrice() {
    }
}