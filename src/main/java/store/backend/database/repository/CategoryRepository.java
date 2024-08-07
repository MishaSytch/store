package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.backend.database.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
