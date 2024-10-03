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
        return priceRepository.save(
                Price.builder()
                .price(price)
                .date(date)
                .build()
        );
    }

    public Price savePrice(Price price) {
        return priceRepository.save(price);
    }

    public Price updatePrice(Long price_id, Price editedPrice) {
        return priceRepository.findById(price_id).map(
                price -> {
                price.setDate(editedPrice.getDate());
                price.setPrice(editedPrice.getPrice());

                priceRepository.save(price);
                return price;
            }
        ).orElse(null);
    }

    public void deletePrice(Long price_id) {
        priceRepository.findById(price_id).ifPresent(
                price -> price.getProduct().removePrice(price)
        );
        priceRepository.deleteById(price_id);
    }
}
