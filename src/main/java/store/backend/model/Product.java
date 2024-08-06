package store.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "product_id")
    private long id;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Price> prices = new ArrayList<>();

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

    @Column(name = "product_year", nullable = false)
    private int year;

    @Column(name = "product_series", nullable = false)
    private String series;

    @Column(name = "product_producer", nullable = false)
    private String producer;

    @Column(name = "product_description", nullable = false)
    private String description;

    @ManyToMany(mappedBy = "products")
    private Set<Category> category;

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
}
