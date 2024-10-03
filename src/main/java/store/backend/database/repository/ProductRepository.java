package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.backend.database.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
