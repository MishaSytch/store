package store.backend.database.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "category_id", updatable = false)
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "category_product",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Product> products;

    public void addProduct(Product product) {
        if (products == null) products = new HashSet<>();
        if (product.getCategories() == null) product.setCategories(new HashSet<>());

        products.add(product);
        product.getCategories().add(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.getCategories().remove(this);
    }

    @ManyToOne
    @JoinColumn(name = "super_category_id")
    @JsonIgnore
    private Category superCategory;

    @OneToMany(
            mappedBy = "superCategory",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<Category> categories;

    public void addCategory(Category category) {
        if (categories == null) categories = new HashSet<>();

        categories.add(category);
        category.setSuperCategory(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.setSuperCategory(null);
    }
}