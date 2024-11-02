package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.backend.database.entity.Category;
import store.backend.database.entity.Product;
import store.backend.database.repository.CategoryRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;

    public Category createCategory(String name) {
        return saveCategory(
                Category.builder()
                    .name(name)
                    .products(new HashSet<>())
                    .categories(new HashSet<>())
                    .build()
        );
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Transactional
    public Category addCategory(Category category, Category addition) {
        if (addition != null && !category.getCategories().contains(addition)) {
            category.addCategory(addition);

            return updateCategory(category);
        }

        return category;
    }

    @Transactional
    public Category addProduct(Category category, Product product) {
        if (product != null && !category.getProducts().contains(product)) {
            category.addProduct(product);

            return updateCategory(category);
        }
        return category;
    }

    public Optional<Category> getCategory(Long category_id) {
        return categoryRepository.findById(category_id);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category updateCategory(Category category) {
        Category existing = categoryRepository
                .findById(category.getId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        for (Category c : category.getCategories()) {
            if (existing.getCategories().stream().noneMatch(x -> x.getId().equals(c.getId()))) {
                existing.addCategory(c);
            }
        }

        for (Product p : category.getProducts()) {
            if (existing.getProducts().stream().noneMatch(x -> x.getId().equals(p.getId()))) {
                existing.addProduct(p);
            }
        }

        if (category.getName() != null && !category.getName().equals(existing.getName())) {
            existing.setName(category.getName());
        }
        if (category.getSuperCategory() != null && !category.getSuperCategory().equals(existing.getSuperCategory())) {
            existing.setSuperCategory(category.getSuperCategory());
        }

        return saveCategory(existing);
    }

    public void deleteCategory(Long category_id) {
        if (category_id != null) getCategory(category_id).ifPresent(category -> categoryRepository.deleteById(category.getId()));
    }
}
