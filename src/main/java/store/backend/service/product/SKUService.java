package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.Product;
import store.backend.database.entity.SKU;
import store.backend.database.repository.SKURepository;

@Service
class SKUService {
    @Autowired
    private SKURepository skuRepository;

    public SKU addSKU(Product product, SKU sku) {
        product.addSKU(sku);

        return sku;
    }

}
