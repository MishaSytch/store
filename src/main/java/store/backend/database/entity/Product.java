package store.backend.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import javax.persistence.*;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id", updatable = false)
    private Long id;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<Price> prices;

    public void addPrice(Price price) {
        if (prices == null) prices = new HashSet<>();

        prices.add(price);
        price.setProduct(this);
    }

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
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "category_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    @JsonIgnore
    private Set<Category> categories;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.MERGE,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )

    private Set<Image> images;

    public void addImage(Image image) {
        if (images == null) images = new HashSet<>();

        images.add(image);
        image.setProduct(this);
    }

    public void removeImage(Image image) {
        images.remove(image);
        image.setProduct(null);
    }

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private List<Order> orders;
}