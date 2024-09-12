package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.Category;
import store.backend.database.entity.Product;
import store.backend.database.repository.CategoryRepository;

import java.util.HashSet;

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
                        .products(new HashSet<>())
                        .categories(new HashSet<>())
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

    public Category addCategory(Long parentCategory_id, Category subCategory) {
        return categoryRepository.findById(parentCategory_id)
                .map(parentCategory -> {
                    parentCategory.addCategory(subCategory);

                    return categoryRepository.save(parentCategory);
                }).orElse(null);
    }

    public Category updateCategory(Long category_id, Category editedCategory) {
        return categoryRepository.findById(category_id)
                .map(
                        category -> {
                            category.setCategories(editedCategory.getCategories());
                            category.setSuperCategory(editedCategory.getSuperCategory());
                            category.setName(editedCategory.getName());
                            category.setProducts(editedCategory.getProducts());

                            return categoryRepository.save(category);
                        }
                ).orElse(null);
    }

    public void deleteCategory(Long superCategory_id, Category category) {
        categoryRepository.findById(superCategory_id)
                .map(
                        c -> {
                            c.removeCategory(category);
                            return category;
                        }
                );
    }

    public Iterable<Product> getProducts(Long category_id) {
        return categoryRepository.findById(category_id).map(Category::getProducts).orElse(null);
    }

    public Product addProduct(Long category_id, Product product) {
        return categoryRepository.findById(category_id)
                .map(
                        category -> {
                            category.addProduct(product);

                            categoryRepository.save(category);
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
                                 if (product.getId().equals(product_id)) {
                                     return productService.updateProduct(product_id, editedProduct);
                                 }
                             }

                             return null;
                        }
                ).orElse(null);
    }

    public void deleteProduct(Long category_id, Product product) {
        categoryRepository.findById(category_id).ifPresent(category -> category.removeProduct(product));
    }
}
