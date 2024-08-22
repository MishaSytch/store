package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import store.backend.database.entity.Category;
import store.backend.database.entity.Product;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query(value = "SELECT * FROM Products WHERE category_id = " +
            "SELECT product FROM Categories WHERE category_id = ?1", nativeQuery = true)
    Iterable<Product> findProductByCategory_id(Long category_id);
}
