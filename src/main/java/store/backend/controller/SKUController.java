package store.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.SKU;
import store.backend.database.repository.SKURepository;

import java.util.Optional;

@RestController
@RequestMapping("/sku")
public class SKUController {
    @Autowired
    private SKURepository skuRepository;

    @GetMapping
    public Iterable<SKU> getSKUs() {
        return skuRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<SKU> getSKU(@PathVariable Long id) {
        return skuRepository.findById(id);
    }

    @GetMapping
    public Iterable<SKU> getProductSKU(@RequestParam Long product_id) {
        return skuRepository.findAllByProductId(product_id);
    }

    @PostMapping
    public SKU postSKU(@RequestBody SKU sku) {
        return skuRepository.save(sku);
    }

    @PutMapping("/{id}")
    public SKU putSKU(@RequestBody SKU editedSku, @PathVariable Long id) {
        Optional<SKU> op_sku = skuRepository.findById(id);

        if (op_sku.isPresent()) {
            SKU sku = op_sku.get();

            sku.setSku(editedSku.getSku());
            sku.setProduct(editedSku.getProduct());

            return sku;
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteSKU(@PathVariable Long id) {
        skuRepository.deleteById(id);
    }
}
