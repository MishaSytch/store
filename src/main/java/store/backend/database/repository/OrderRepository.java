package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.backend.database.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
