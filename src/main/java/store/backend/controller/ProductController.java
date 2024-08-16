package store.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.Product;
import store.backend.database.repository.ProductRepository;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable("id") Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @GetMapping
    public Iterable<Product> getProducts(@RequestParam("category_id") Long category_id) {
        return productRepository.findAllByCategoryId(category_id).orElse(null);
    }

    @GetMapping("/all")
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public Product postProduct(@RequestParam Product product) {
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@RequestParam("id") Long id) {
        productRepository.deleteById(id);
    }

}
