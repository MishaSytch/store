package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import store.backend.database.entity.Customer;
import store.backend.database.entity.Order;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);

    @Query(value = "SELECT * FROM Orders WHERE customer_id = ?1", nativeQuery = true)
    Iterable<Order> findOrdersByCustomer_id(Long customer_id);
}
