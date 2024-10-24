package store.backend.service.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.backend.database.entity.Category;
import store.backend.database.loader.DBLoader;
import store.backend.database.repository.CategoryRepository;

import java.util.HashSet;

@SpringBootTest
class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;
    private final String name = "CategoryTest";


    @Test
    void createCategory() {
        for (Category category : categoryService.getCategories()) {
            Assertions.assertNotEquals(name, category.getName());
        }
        categoryService.createCategory(name);
        int count = 0;
        Category category = null;
        for (Category c : categoryService.getCategories()) {
            if (c.getName().equals(name)) {
                category = c;
                count++;
            }
        }
        Assertions.assertEquals(1, count);
        categoryService.deleteCategory(category.getId());
    }

    @Test
    void saveCategory() {
        String name_1 = name + "Save";
        for (Category category : categoryService.getCategories()) {
            Assertions.assertNotEquals(name, category.getName());
        }
        Category category = Category.builder()
                .name(name_1)
                .products(new HashSet<>())
                .categories(new HashSet<>())
                .build();

        categoryService.saveCategory(category);
        int count = 0;

        for (Category c : categoryService.getCategories()) {
            if (c.getName().equals(name_1)) count++;
        }

        Assertions.assertEquals(1, count);

        categoryService.deleteCategory(category.getId());

    }

    @Test
    void getCategory() {
        String name_1 = name + "Get";
        for (Category category : categoryService.getCategories()) {
            Assertions.assertNotEquals(name, category.getName());
        }

        Category category = categoryService.createCategory(name_1);
        Assertions.assertTrue(categoryService.getCategory(category.getId()).isPresent());
        Assertions.assertEquals(category.getName(), categoryService.getCategory(category.getId()).get().getName());

        categoryService.deleteCategory(category.getId());
    }

    @Test
    void getCategories() {
        int count = 0;
        Iterable<Category> categories = categoryService.getCategories();
        for (Category category : categories) {
            count++;
        }

        Assertions.assertEquals(9, count);

    }

    @Test
    void updateCategory() {
        String name_1 = name + "Update";
        String name_new = name + "New";
        Category category = categoryService.createCategory(name_1);
        category.setName(name_new);
        categoryService.updateCategory(category);

        Assertions.assertTrue(categoryService.getCategory(category.getId()).isPresent());
        Assertions.assertEquals(name_new, categoryService.getCategory(category.getId()).get().getName());
    }

    @Test
    void deleteCategory() {
        String name_1 = name + "Delete";
        Category category = categoryService.createCategory(name_1);
        Assertions.assertTrue(categoryService.getCategory(category.getId()).isPresent());
        Assertions.assertEquals(name_1, categoryService.getCategory(category.getId()).get().getName());

        categoryService.deleteCategory(category.getId());
        Assertions.assertNull(categoryService.getCategory(category.getId()));
    }
}