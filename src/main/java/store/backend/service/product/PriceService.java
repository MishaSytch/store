package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.Price;
import store.backend.database.entity.Product;
import store.backend.database.repository.PriceRepository;

import java.math.BigDecimal;
import java.util.Date;

@Service
class PriceService {
    @Autowired
    private PriceRepository priceRepository;

    public Price createPrice(BigDecimal price, Date date) {

        return Price.builder()
                .price(price)
                .date(date)
                .build();
    }

    public Price addPrice(Product product, Price price) {
        product.addPrice(price);

        return price;
    }

    public Price updatePrice(Long price_id, Price editedPrice) {
        return priceRepository.findById(price_id).map(
                price -> {
                price.setDate(editedPrice.getDate());
                price.setPrice(editedPrice.getPrice());

                return price;
            }
        ).orElse(null);
    }
}
