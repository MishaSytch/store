package store.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customer_id")
    private long id;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Order> orders = new ArrayList<>();

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

    private String name;

    private String surname;

    private String email;

    private String password;
}
