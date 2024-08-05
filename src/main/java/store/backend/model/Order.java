package store.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private long id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private Customer customer;

    private String date;

    @ManyToMany (
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> products = new HashSet<>();

    @Transactional
    public void addProduct(Product product) {
        products.add(product);
    }

    @Transactional
    public void removeProduct(Product product) {
        products.remove(product);
        product.getOrders().remove(this);
    }
}
