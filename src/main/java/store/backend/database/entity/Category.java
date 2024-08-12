package store.backend.database.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Categories")
public class Category {
    public Category(String name) {
        this.name = name;
    }

    public Category(String name, Iterable<Product> products) {
        this.name = name;
        for (Product product : products) {
            addProduct(product);
        }
    }

    public Category(String name, Set<Category> categories) {
        this.name = name;
        for (Category category : categories) {
            addCategory(category);
        }
    }

    public Category(String name, Set<Category> categories, Iterable<Product> products) {
        this.name = name;
        for (Category category : categories) {
            addCategory(category);
        }
        for (Product product : products) {
            addProduct(product);
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name", nullable = false)
    private String name;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
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
    private Category superCategory;

    @OneToMany(
            mappedBy = "category",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
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
