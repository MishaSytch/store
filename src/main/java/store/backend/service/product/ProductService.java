package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.*;
import store.backend.database.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PriceService priceService;
    @Autowired
    private ImageService imageService;

    public Product createProduct(String name, String description, String sku, Long quantity) {
        return productRepository.save(
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

    public Product updateProduct(Long product_id, Product editedProduct) {
        return productRepository
                .findById(product_id)
                .map(
                    product -> {
                        product.setCategories(editedProduct.getCategories());
                        product.setName(editedProduct.getName());
                        product.setPrices(editedProduct.getPrices());
                        product.setImages(editedProduct.getImages());
                        product.setSKU(editedProduct.getSKU());
                        product.setDescription(editedProduct.getDescription());

                        return productRepository.save(product);
                    }
                ).orElse(null);
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

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public void deleteProduct(Long product_id) {
        productRepository.deleteById(product_id);
    }

    public Price createPrice(BigDecimal price, Date date) {
        return priceService.createPrice(price, date);
    }

    public Price getCurrentPrice(Long product_id) {
        return productRepository.findById(product_id).flatMap(product -> product.getPrices().stream().max(Comparator.comparing(Price::getDate))).orElse(null);
    }

    public Price updatePrice(Long price_id, Price editedPrice) {
        return priceService.updatePrice(price_id, editedPrice);
    }

    public void deletePrice(Long price_id) {
        priceService.deletePrice(price_id);
    }

    public Image createImage(String name, String ref) {
        return imageService.createImage(name, ref);
    }

    public Image updateImage(Long image_id, Image editedImage) {
        return imageService.updateImage(image_id, editedImage);
    }

    public void deleteImage(Long image_id) {
        imageService.deleteImage(image_id);
    }
}
