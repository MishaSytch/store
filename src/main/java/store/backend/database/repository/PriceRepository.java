package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.backend.database.entity.Price;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {
    Iterable<Price> findAllByProductId(Long productId);
}
