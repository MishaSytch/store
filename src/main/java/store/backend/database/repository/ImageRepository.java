package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.backend.database.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}
