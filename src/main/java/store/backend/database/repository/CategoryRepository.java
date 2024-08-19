package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.backend.database.entity.Category;
import store.backend.database.entity.Product;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Iterable<Product> findAllProductById(Long id);

    Optional<Category> findByName(String name);
}
