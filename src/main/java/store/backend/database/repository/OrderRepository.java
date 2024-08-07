package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.backend.database.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
