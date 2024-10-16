package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.backend.database.entity.Category;
import store.backend.database.entity.Product;
import store.backend.database.repository.CategoryRepository;

import java.util.HashSet;
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

    public Category addCategory(Category category, Category addition) {
        category.addCategory(addition);

        return category;

//        return updateCategory(category);
    }

    public Category addProduct(Category category, Product product) {
        category.addProduct(product);

        return category;

//        return updateCategory(category);
    }

    public Optional<Category> getCategory(Long category_id) {
        return categoryRepository.findById(category_id);
    }

    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public Category updateCategory(Category category) {
        assert categoryRepository.findById(category.getId()).isPresent();

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long category_id) {
        if (category_id != null) getCategory(category_id).ifPresent(category -> categoryRepository.deleteById(category.getId()));
    }
}
