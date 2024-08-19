package store.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.Product;
import store.backend.database.repository.ProductRepository;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/{id}")
    public Optional<Product> getProduct(@PathVariable("id") Long id) {
        return productRepository.findById(id);
    }

    @GetMapping("/all")
    public Iterable<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @PostMapping
    public Product postProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
    }

}
