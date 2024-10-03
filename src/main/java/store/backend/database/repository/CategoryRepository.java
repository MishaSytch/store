package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.backend.database.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
