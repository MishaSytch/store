package store.backend.database.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import java.util.ArrayList;
import java.util.List;
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
            cascade = CascadeType.PERSIST,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    @Builder.Default
    private Set<Price> prices = new HashSet<>();

    public void addPrice(Price price) {
        if (price != null) {
            prices.add(price);
            price.setProduct(this);
        }
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
            mappedBy = "products",
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    @JsonBackReference
    @Builder.Default
    private Set<Category> categories = new HashSet<>();

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.PERSIST,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    @Builder.Default
    private Set<Image> images = new HashSet<>();

    public void addImage(Image image) {
        if (image != null) {
            images.add(image);
            image.setProduct(this);
        }
    }

    public void removeImage(Image image) {
        images.remove(image);
        image.setProduct(null);
    }

    @ManyToMany(mappedBy = "products")
    @JsonBackReference
    @Builder.Default
    private List<Order> orders = new ArrayList<>();
}