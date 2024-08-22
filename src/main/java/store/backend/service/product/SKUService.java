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

    public SKU createSKU(String sku) {
        return SKU.builder()
                .sku(sku)
                .build();
    }

    public SKU updateSKU(Long sku_id, SKU editedSKU) {
        return skuRepository.findById(sku_id).map(
                sku -> {
                    sku.setSku(editedSKU.getSku());

                    return sku;
                }
        ).orElse(null);
    }

    public void deleteSKU(Long sku_id) {
        skuRepository.findById(sku_id).ifPresent(
                sku -> sku.getProduct().removeSKU(sku)
        );
    }
}
