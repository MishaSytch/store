package store.backend.database.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.net.URL;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(name = "image_title", nullable = false)
    private String name;

    @Column(name = "image_reference", nullable = false)
    private URL reference;
}
