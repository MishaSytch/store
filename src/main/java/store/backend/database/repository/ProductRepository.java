package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import store.backend.database.entity.Price;
import store.backend.database.entity.Product;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Iterable<Product> findByCategory_id(Long categoryId);

    Optional<Product> findByName(String name);

    Iterable<Product> findAllByName(String name);

    @Query("SELECT price FROM Price WHERE product.id = ?1 ORDER BY date DESC")
    Optional<Price> findCurrentPriceByProduct_id(Long product_id);
}
