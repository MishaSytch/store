package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.Price;
import store.backend.database.entity.Product;
import store.backend.database.repository.PriceRepository;

@Service
class PriceService {
    @Autowired
    private PriceRepository priceRepository;

    public Price addPrice(Product product, Price price) {
        product.addPrice(price);

        return price;
    }
}
