package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.backend.database.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
