package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.backend.database.entity.Image;
import store.backend.database.entity.Price;
import store.backend.database.entity.Product;
import store.backend.database.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.util.Comparator;

@Service
public class ProductService {
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

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProduct(Long product_id) {
        return productRepository.findById(product_id);
    }

    public List<Product> getProductsById(List<Long> products_id) {
        return productRepository.findAllById(products_id);
    }

    public void addQuantity(Product product, Long quantity) {
        product.setQuantity(product.getQuantity() + quantity);
        productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Long product_id) {
        productRepository.deleteById(product_id);
    }

    public Price createPrice(BigDecimal price, Date date) {
        return priceService.createPrice(price, date);
    }

    public Product addPrice(Product product, Price price) {
        product.addPrice(price);

        return saveProduct(product);
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

    public Product addImage(Product product, Image image) {
        product.addImage(image);

        return saveProduct(product);
    }

    public Image updateImage(Image image) {
        return imageService.updateImage(image);
    }

    public void deleteImage(Long image_id) {
        imageService.deleteImage(image_id);
    }
}
