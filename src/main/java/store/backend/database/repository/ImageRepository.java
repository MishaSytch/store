package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import store.backend.database.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
