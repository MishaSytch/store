package store.backend.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import store.backend.database.entity.Product;
import store.backend.database.entity.SKU;

@Repository
public interface SKURepository extends JpaRepository<SKU, Long> {
    Iterable<SKU> findAllByProduct_Id(Long product_id);

    @Query("SELECT sku FROM SKU WHERE sku.order IS EMPTY AND sku.product.product_id = product_id")
    Iterable<SKU> findAvailableSKUByProduct_id(Long product_id);
}
