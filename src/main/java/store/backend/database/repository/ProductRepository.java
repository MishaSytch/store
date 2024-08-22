package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import store.backend.database.entity.Image;
import store.backend.database.entity.Price;
import store.backend.database.entity.Product;
import store.backend.database.entity.SKU;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Iterable<Product> findAllByCategory_id(Long categoryId);

    Optional<Product> findByName(String name);

    Iterable<Product> findAllByName(String name);

    Iterable<Price> findAllPricesByProduct_id(Long product_id);

    Iterable<Image> findAllImagesByProduct_id(Long product_id);

    Iterable<SKU> findAllSKUsByProduct_id(Long product_id);

    Optional<Price> findFirstPriceByProduct_idOrderByDate(Long productId);
}
