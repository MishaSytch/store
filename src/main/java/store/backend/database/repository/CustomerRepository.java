package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.backend.database.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
