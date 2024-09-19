package store.backend.database.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;

    @Column(name = "order_date", nullable = false)
    private Date date;

    @ManyToMany
    @JoinTable(
            name = "order_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )

    private List<Product> products;

    @Transactional
    public void addProduct(Product product) {
        if (products == null) products = new ArrayList<>();

        products.add(product);
        product.setQuantity(product.getQuantity() - 1);
        product.getOrders().add(this);
    }

    @Transactional
    public void removeProduct(Product product) {
        products.remove(product);
        product.setQuantity(product.getQuantity() + 1);
        product.getOrders().remove(this);
    }
}