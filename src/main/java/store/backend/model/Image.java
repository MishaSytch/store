package store.backend.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.print.DocFlavor.URL;

@Entity
@Data
@NoArgsConstructor
@Table(name = "Images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Column(name = "image_title", nullable = false)
    private String name;

    @Column(name = "image_reference", nullable = false)
    private URL reference;
}
