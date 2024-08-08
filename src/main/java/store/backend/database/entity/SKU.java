package store.backend.database.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "SKU")
public class SKU {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "sku_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(name = "sku", nullable = false)
    private String sku;
}
