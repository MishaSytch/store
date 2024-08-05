package store.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Price_History")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long price_id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    private float price;

    private Date date;
}
