package store.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.Price;
import store.backend.database.repository.PriceRepository;

import java.util.Optional;

@RestController
@RequestMapping("/price")
public class PriceController {
    @Autowired
    private PriceRepository priceRepository;

    @GetMapping("/{id}")
    public Optional<Price> getPrice(@PathVariable("id") Long id) {
        return priceRepository.findById(id);
    }

    @GetMapping
    public Iterable<Price> getProductPrice(@RequestParam Long product_id) {
        return priceRepository.findAllByProductId(product_id);
    }

    @PostMapping
    public Price postPrice(@RequestBody Price price) {
        return priceRepository.save(price);
    }

    @PutMapping("/{id}")
    public Price putPrice(@RequestBody Price editedPrice, @PathVariable("id") Long id) {
        Optional<Price> op_price = priceRepository.findById(id);

        if (op_price.isPresent()) {
            Price price = op_price.get();

            price.setProduct(editedPrice.getProduct());
            price.setPrice(editedPrice.getPrice());
            price.setDate(editedPrice.getDate());

            return price;
        }

        return null;
    }

    @DeleteMapping("/{id}")
    public void deletePrice(@PathVariable("id") Long id) {
        priceRepository.deleteById(id);
    }
}
