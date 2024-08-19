package store.backend.database.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id")
    private Long id;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<Price> prices = new HashSet<>();


    @Transactional
    public void addPrice(Price price) {
        prices.add(price);
        price.setProduct(this);
    }

    @Transactional
    public void removePrice(Price price) {
        prices.remove(price);
        price.setProduct(null);
    }

    @ManyToMany(mappedBy = "products")
    @JsonManagedReference
    private Set<Order> orders = new HashSet<>();

    @Column(name = "product_name", nullable = false, unique = true)
    private String name;

    @Column(name = "product_description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "products")
    @JsonManagedReference
    private Set<Category> category = new HashSet<>();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    private Set<Image> images = new HashSet<>();
    @Transactional
    public void addImage(Image image) {
        images.add(image);
        image.setProduct(this);
    }
    @Transactional
    public void removeImage(Image image) {
        images.remove(image);
        image.setProduct(null);
    }

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    private Set<SKU> skus = new HashSet<>();
    @Transactional
    public void addSKU(SKU sku) {
        skus.add(sku);
        sku.setProduct(this);
    }
    @Transactional
    public void removeSKU(SKU sku) {
        skus.remove(sku);
        sku.setProduct(null);
    }
}
