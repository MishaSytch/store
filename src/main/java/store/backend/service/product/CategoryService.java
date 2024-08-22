package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.Category;
import store.backend.database.entity.Product;
import store.backend.database.repository.CategoryRepository;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductService productService;

    public Category createCategory(String name) {
        return categoryRepository.save(
                Category.builder()
                        .name(name)
                        .build()
        );
    }

    public Category getCategory(Long category_id) {
        return categoryRepository.findById(category_id).orElse(null);
    }

    public Iterable<Category> getSubCategories(Long category_id) {
        return categoryRepository.findById(category_id).map(Category::getCategories).orElse(null);
    }

    public Iterable<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category addCategory(Long category_id, Category category) {
        return categoryRepository.findById(category_id)
                .map(
                        c -> {
                            c.addCategory(category);

                            return category;
                        }
                ).orElse(null);
    }

    public Category updateCategory(Long category_id, Category editedCategory) {
        return categoryRepository.findById(category_id)
                .map(
                        category -> {
                            category.setCategories(editedCategory.getCategories());
                            category.setSuperCategory(editedCategory.getSuperCategory());
                            category.setName(editedCategory.getName());
                            category.setProducts(editedCategory.getProducts());

                            return category;
                        }
                ).orElse(null);
    }

    public void deleteCategory(Long superCategory_id, Category category) {
        if (categoryRepository.findById(superCategory_id)
                .map(
                        c -> {
                            c.removeCategory(category);
                            return category;
                        }
                ).orElse(null) == null) categoryRepository.deleteById(category.getId());
    }

    public Product getProduct(Long category_id, Long product_id) {
        Iterable<Product> products = categoryRepository.findAllProductByCategory_id(category_id);
        for (Product product : products) {
            if (product.getId().equals(product_id)) return product;
        }

        return null;
    }

    public Iterable<Product> getProducts(Long category_id) {
        return categoryRepository.findById(category_id).map(Category::getProducts).orElse(null);
    }

    public Product addProduct(Long category_id, Product product) {
        return categoryRepository.findById(category_id)
                .map(
                        category -> {
                            category.addProduct(product);

                            return product;
                        }
                ).orElse(null);
    }

    public Product updateProduct(Long category_id, Long product_id, Product editedProduct) {
        return categoryRepository.findById(category_id)
                .map(
                        category -> {
                             Iterable<Product> products = category.getProducts();

                             for (Product product : products) {
                                 if (product.getId().equals(product_id)) return productService.updateProduct(product_id, editedProduct);
                             }

                             return null;
                        }
                ).orElse(null);
    }

    public void deleteProduct(Long category_id, Product product) {
        categoryRepository.findById(category_id).ifPresent(category -> category.removeProduct(product));
    }
}
