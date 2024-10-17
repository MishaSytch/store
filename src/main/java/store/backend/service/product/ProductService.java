package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.backend.database.entity.Image;
import store.backend.database.entity.Price;
import store.backend.database.entity.Product;
import store.backend.database.repository.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.util.Comparator;

@Service
public class ProductService {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PriceService priceService;
    @Autowired
    private ImageService imageService;

    public Product createProduct(String name, String description, String sku, Long quantity) {
        return saveProduct(
                Product.builder()
                    .name(name)
                    .description(description)
                    .SKU(sku)
                    .quantity(quantity)
                    .categories(new HashSet<>())
                    .images(new HashSet<>())
                    .prices(new HashSet<>())
                    .orders(new ArrayList<>())
                    .build()
        );
    }

    @Transactional
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Product product) {
        if (entityManager.find(Product.class, product.getId()) != null) {
            entityManager.merge(product);
        } else {
            saveProduct(product);
        }

        return product;
    }

    public Optional<Product> getProduct(Long product_id) {
        return productRepository.findById(product_id);
    }

    public List<Product> getProductsById(List<Long> products_id) {
        return productRepository.findAllById(products_id);
    }

    public void addQuantity(Product product, Long quantity) {
        product.setQuantity(product.getQuantity() + quantity);

        saveProduct(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long product_id) {
        if (product_id != null) getProduct(product_id).ifPresent(product -> productRepository.deleteById(product.getId()));
    }

    public Price createPrice(BigDecimal price, Date date) {
        return priceService.createPrice(price, date);
    }

    @Transactional
    public Product addPrice(Product product, Price price) {
        if (!product.getPrices().contains(price)) {
            product.addPrice(price);

            return updateProduct(product);
        }
        return product;
    }

    public Price getCurrentPrice(Long product_id) {
        return productRepository.findById(product_id).flatMap(product -> product.getPrices().stream().max(Comparator.comparing(Price::getDate))).orElse(null);
    }

    public Price updatePrice(Price price) {
        return priceService.updatePrice(price);
    }

    public void deletePrice(Long price_id) {
        priceService.deletePrice(price_id);
    }

    public Image createImage(String name, String ref) {
        return imageService.createImage(name, ref);
    }

    @Transactional
    public Product addImage(Product product, Image image) {
        if (!product.getImages().contains(image)) {
            product.addImage(image);

            return saveProduct(product);
        }
        return product;
    }

    public Image updateImage(Image image) {
        return imageService.updateImage(image);
    }

    public void deleteImage(Long image_id) {
        imageService.deleteImage(image_id);
    }
}
