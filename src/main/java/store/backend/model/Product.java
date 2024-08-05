package store.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long product_id;

    @OneToMany(
            mappedBy = "Price_History",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Price> prices = new ArrayList<>();
    public void addPrice(Price price) {
        prices.add(price);
        price.setProduct(this);
    }
    public void removePrice(Price price) {
        prices.remove(price);
        price.setProduct(null);
    }

    @ManyToMany (mappedBy = "Order")
    private Set<Order> orders = new HashSet<>();

    private String name;

    private int year;

    private Type type;

    private String series;

    private String producer;

    private String description;
}
