package store.backend.database.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)

    @JsonBackReference
    private Customer customer;

    @Column(name = "order_date", nullable = false)
    private Date date;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.MERGE,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    @JsonManagedReference
    private Set<SKU> skus = new HashSet<>();
    @Transactional
    public void addSKU(SKU sku) {
        skus.add(sku);
        sku.setOrder(this);
    }
    @Transactional
    public void removeSKU(SKU sku) {
        skus.remove(sku);
        sku.setOrder(null);
    }
}
