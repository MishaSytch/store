package store.backend.database.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private Customer customer;

    @Column(name = "order_date", nullable = false)
    private String date;

    @ManyToMany (cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonBackReference
    private Set<Product> products = new HashSet<>();

    @Transactional
    public void addProduct(Product product) {
        products.add(product);
        product.getOrders().add(this);
    }

    @Transactional
    public void removeProduct(Product product) {
        products.remove(product);
        product.getOrders().remove(this);
    }
}
