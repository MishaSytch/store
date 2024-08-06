package store.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "price_id")
    private long id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    private Product product;

    @Column(name = "price_value", nullable = false)
    private BigDecimal price;

    @Column(name = "price_date", nullable = false)
    private Date date;
}
