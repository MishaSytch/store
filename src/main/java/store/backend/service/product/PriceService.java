package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.Price;
import store.backend.database.repository.PriceRepository;

import java.math.BigDecimal;
import java.util.Date;

@Service
class PriceService {
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

        return priceRepository.save(price);
    }

    public void deletePrice(Long price_id) {
        if (price_id != null) priceRepository.findById(price_id).ifPresent(
                price -> {
                    price.getProduct().removePrice(price);
                    priceRepository.deleteById(price_id);
                }
        );
    }
}
