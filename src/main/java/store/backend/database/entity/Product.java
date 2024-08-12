package store.backend.database.entity;

import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Products")
public class Product {
    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Product(String name, String description, Iterable<Price> prices, Iterable<Image> images) {
        this.name = name;
        this.description = description;
        for (Price price : prices) {
            addPrice(price);
        }
        for (Image image : images) {
            addImage(image);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id")
    private Long id;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
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
    private Set<Order> orders = new HashSet<>();

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "products")
    private Set<Category> category = new HashSet<>();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
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
            orphanRemoval = true
    )
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
