package store.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.Category;
import store.backend.database.entity.Product;
import store.backend.database.repository.CategoryRepository;

import java.util.Collections;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/{id}/products")
    public Iterable<Product> getProductByCategoryId(@PathVariable Long id) {
        return categoryRepository.findAllProductByCategoryId(id);
    }

    @GetMapping("/{id}")
    public Iterable<Category> getCategoryById(@PathVariable Long id) {
        return categoryRepository.findAllById(Collections.singleton(id));
    }

    @GetMapping("/all")
    public Iterable<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

}
