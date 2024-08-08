package store.backend.database.entity;

import lombok.Data;
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
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customer_id")
    private Long id;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Order> orders = new HashSet<>();
    @Transactional
    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }
    @Transactional
    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null);
    }

    @Column(name = "customer_name", nullable = false)
    private String name;

    @Column(name = "customer_surname", nullable = false)
    private String surname;

    @Column(name = "customer_email", nullable = false)
    private String email;
}
