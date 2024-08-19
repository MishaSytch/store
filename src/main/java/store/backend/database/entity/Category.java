package store.backend.database.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
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

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "category",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonBackReference
    private Set<Product> products = new HashSet<>();
    @Transactional
    public void addProduct(Product product) {
        products.add(product);
        product.getCategory().add(this);
    }
    @Transactional
    public void removeProduct(Product product) {
        products.remove(product);
        product.getCategory().remove(this);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Category superCategory;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
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
