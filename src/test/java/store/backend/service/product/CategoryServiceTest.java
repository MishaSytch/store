package store.backend.service.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.backend.database.entity.Category;
import store.backend.database.repository.CategoryRepository;

import java.util.HashSet;

@SpringBootTest
class CategoryServiceTest {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;

    private final String name = "CategoryTest";

    @Test
    void createCategory() {
        Assertions.assertTrue(categoryRepository.findAll().stream().noneMatch(c -> c.getName().equals(name)));
        categoryService.createCategory(name);
        Assertions.assertEquals(1, categoryRepository.findAll().stream().filter(c -> c.getName().equals(name)).count());
    }

    @Test
    void saveCategory() {
        String name_1 = name + "Save";
        Assertions.assertTrue(categoryRepository.findAll().stream().noneMatch(c -> c.getName().equals(name_1)));
        Category category = Category.builder()
                .name(name_1)
                .products(new HashSet<>())
                .categories(new HashSet<>())
                .build();
        categoryService.saveCategory(category);
        Assertions.assertEquals(1, categoryRepository.findAll().stream().filter(c -> c.getName().equals(name_1)).count());
    }

    @Test
    void getCategory() {
        String name_1 = name + "Get";
        Category category = categoryService.createCategory(name_1);
        Assertions.assertEquals(category.getName(), categoryService.getCategory(category.getId()).getName());
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
        categoryService.updateCategory(category.getId(), category);

        Assertions.assertEquals(name_new, categoryService.getCategory(category.getId()).getName());
    }

    @Test
    void deleteCategory() {
        String name_1 = name + "Delete";
        Category category = categoryService.createCategory(name_1);
        Assertions.assertEquals(name_1, categoryService.getCategory(category.getId()).getName());

        categoryService.deleteCategory(category.getId());
        Assertions.assertNull(categoryService.getCategory(category.getId()));
    }
}