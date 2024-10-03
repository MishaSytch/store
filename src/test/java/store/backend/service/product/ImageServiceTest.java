package store.backend.service.product;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import store.backend.database.entity.Image;
import store.backend.database.entity.Product;
import store.backend.database.repository.ImageRepository;
import store.backend.database.repository.ProductRepository;

@SpringBootTest
class ImageServiceTest {
    @Autowired
    private ImageService imageService;
    @Autowired
    private ImageRepository imageRepository;

    private final String name = "ImageName";

    @Test
    void createImage() {
        Assertions.assertTrue(imageRepository.findAll().stream().noneMatch(i -> i.getName().equals(name)));
        imageService.createImage(name, name);
        Assertions.assertEquals(1, imageRepository.findAll().stream().filter(c -> c.getName().equals(name)).count());
    }

    @Test
    void saveImage() {
        String name_1 = name + "Save";
        Assertions.assertTrue(imageRepository.findAll().stream().noneMatch(i -> i.getName().equals(name_1)));
        Image image = Image.builder()
                .name(name_1)
                .reference(name_1)
                .build();
        imageService.saveImage(image);
        Assertions.assertEquals(1, imageRepository.findAll().stream().filter(i -> i.getName().equals(name_1)).count());
    }

    @Test
    void updateImage() {
        String name_1 = name + "Update";
        String name_new = name + "New";
        Image image = imageService.createImage(name_1, name_1);
        image.setName(name_new);
        imageService.updateImage(image.getId(), image);

        Assertions.assertEquals(name_new, imageService.getImage(image.getId()).getName());
    }

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    @Transactional
    void deleteImage() {
        String name_1 = name + "Delete";
        Image image = imageService.createImage(name_1, name_1);
        Product p = productService.createProduct("Product", "Desc", "sku", 1L);
        p.addImage(image);
        productRepository.save(p);

        imageService.deleteImage(image.getId());
        Assertions.assertNull(imageService.getImage(image.getId()));
    }
}