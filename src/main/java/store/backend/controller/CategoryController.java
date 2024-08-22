package store.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.Category;
import store.backend.database.entity.Product;
import store.backend.service.product.CategoryService;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}/product/all")
    public Iterable<Product> getProducts(@PathVariable("id") Long category_id) {
        return categoryService.getProducts(category_id);
    }

    @GetMapping("/{id}")
    public Iterable<Category> getCategory(@PathVariable Long id) {
        return categoryService.getSubCategories(id);
    }

    @GetMapping("/all")
    public Iterable<Category> getCategories() {
        return categoryService.getCategories();
    }

}
