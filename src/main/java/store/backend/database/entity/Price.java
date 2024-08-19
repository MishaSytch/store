package store.backend.database.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Prices")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "price_id")
    private Long id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private Product product;

    @Column(name = "price_value", nullable = false)
    private BigDecimal price;

    @Column(name = "price_date", nullable = false)
    private Date date;
}
