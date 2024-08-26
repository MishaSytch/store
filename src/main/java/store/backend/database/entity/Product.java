package store.backend.database.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@AllArgsConstructor
@Builder
@Table(name = "Products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
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

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_description", nullable = false)
    private String description;

    @Column(name = "product_sku", nullable = false)
    private String SKU;

    @Column(name = "product_quantity", nullable = false)
    private Long quantity;

    @ManyToMany(
            mappedBy = "products",
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JsonBackReference
    private Set<Category> categories = new HashSet<>();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.MERGE,
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

    @ManyToMany(mappedBy = "products")
    @JsonBackReference
    private List<Order> orders = new ArrayList<>();
}