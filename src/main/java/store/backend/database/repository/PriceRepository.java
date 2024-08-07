package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.backend.database.entity.Price;

public interface PriceRepository extends JpaRepository<Price, Long> {
}
