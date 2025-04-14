package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.Price;
import store.backend.database.repository.PriceRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class PriceService {
    @Autowired
    private PriceRepository priceRepository;

    public Price createPrice(BigDecimal price, Date date) {
        return savePrice(
                Price.builder()
                    .price(price)
                    .date(date)
                    .build()
        );
    }

    public Price savePrice(Price price) {
        return priceRepository.save(price);
    }

    public Price updatePrice(Price price) {
        assert priceRepository.findById(price.getId()).isPresent();

        return savePrice(price);
    }

    public void deletePrice(Long price_id) {
        priceRepository.findById(price_id).ifPresent(
                price -> {
                    if (price.getProduct() != null) price.getProduct().removePrice(price);
                    priceRepository.deleteById(price_id);
                }
        );
    }

    public List<Price> getPrices() {
        return priceRepository.findAll();
    }


    public Price getPrice(Long price_id) {
        return priceRepository.findById(price_id).orElse(null);
    }
}
