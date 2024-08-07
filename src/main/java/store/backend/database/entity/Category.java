package store.backend.database.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Categories")
public class Category {

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
    private Set<Product> products;
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

//    @ManyToOne(fetch = FetchType.LAZY)
//    private Category superCategory;
//
//    @OneToMany(
//            mappedBy = "category",
//            cascade = CascadeType.ALL,
//            orphanRemoval = true
//    )
//    private Set<Category> categories = new HashSet<>();
//    @Transactional
//    public void addCategory(Category category) {
//        categories.add(category);
//        category.setSuperCategory(this);
//    }
//    @Transactional
//    public void removeCategory(Category category) {
//        categories.remove(category);
//        category.setSuperCategory(null);
//    }
}
