package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.backend.database.entity.SKU;

public interface SKURepository extends JpaRepository<SKU, Long> {
}
