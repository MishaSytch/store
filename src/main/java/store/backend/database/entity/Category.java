package store.backend.database.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "Categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @ManyToMany(
            cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "category_product",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonManagedReference
    private Set<Product> products = new HashSet<>();

    @Transactional
    public void addProduct(Product product) {
        products.add(product);
        product.getCategories().add(this);
    }

    @Transactional
    public void removeProduct(Product product) {
        products.remove(product);
        product.getCategories().remove(this);
    }

    @ManyToOne
    @JoinColumn(name = "super_category_id")
    private Category superCategory;

    @OneToMany(
            mappedBy = "superCategory",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    private Set<Category> categories = new HashSet<>();

    @Transactional
    public void addCategory(Category category) {
        categories.add(category);
        category.setSuperCategory(this);
    }

    @Transactional
    public void removeCategory(Category category) {
        categories.remove(category);
        category.setSuperCategory(null);
    }
}