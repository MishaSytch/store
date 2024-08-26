package store.backend.database.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import store.backend.security.role.Role;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Customers")
public class Customer extends AbstractUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "customer_id")
    private Long id;

    @OneToMany(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
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

    @Column(name = "customer_first_name", nullable = false)
    private String firstName;

    @Column(name = "customer_last_name", nullable = false)
    private String lastName;

    @Column(name = "customer_password", nullable = false)
    private String password;

    @Column(name = "customer_email", nullable = false)
    private String email;

    @Override
    public String getUsername() {
        return email;
    }
}
