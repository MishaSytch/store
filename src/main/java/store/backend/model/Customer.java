package store.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long customer_id;

    @OneToMany(
            mappedBy = "Order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Order> orders = new ArrayList<>();
    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }
    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null);
    }

    private String name;

    private String surname;

    private String email;

    private String password;
}
