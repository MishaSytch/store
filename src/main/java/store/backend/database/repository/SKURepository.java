package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.backend.database.entity.SKU;

@Repository
public interface SKURepository extends JpaRepository<SKU, Long> {
    Iterable<SKU> findAllByProductId(Long productId);
}
