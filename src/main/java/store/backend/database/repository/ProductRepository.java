package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import store.backend.database.entity.Image;
import store.backend.database.entity.Price;
import store.backend.database.entity.Product;
import store.backend.database.entity.SKU;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Iterable<Product> findAllByCategoryId(Long categoryId);

    Optional<Product> findByName(String name);

    Iterable<Product> findAllByName(String name);

    Iterable<Price> findAllPricesByProduct_id(Long product_id);

    Iterable<Image> findAllImagesByProduct_id(Long product_id);

    Iterable<SKU> findAllSKUsByProduct_id(Long product_id);
}
