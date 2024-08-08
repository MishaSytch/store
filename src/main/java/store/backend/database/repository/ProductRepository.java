package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.backend.database.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Iterable<Product> getProductsByCategory_Id(Long categoryId);
}
