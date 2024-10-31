package store.backend.service.product;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import store.backend.database.entity.*;
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

    private final ProductRepository productRepository;
    private final PriceService priceService;
    private final ImageService imageService;

    @Autowired
    public ProductService(ProductRepository productRepository, PriceService priceService, ImageService imageService) {
        this.productRepository = productRepository;
        this.priceService = priceService;
        this.imageService = imageService;
    }


    public Product createProduct(String name, String description, String sku, Long quantity) {
        Product product = Product.builder()
                .name(name)
                .description(description)
                .SKU(sku)
                .quantity(quantity)
                .orders(new ArrayList<>())
                .categories(new HashSet<>())
                .prices(new HashSet<>())
                .images(new HashSet<>())
                .build();

        return saveProduct(product);
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public Product updateProduct(Product product) {
        Product existing = productRepository.findById(product.getId()).orElseThrow(() -> new IllegalArgumentException("Product not found"));

        existing.setOrders(new ArrayList<>());
        existing.setCategories(new HashSet<>());
        existing.setImages(new HashSet<>());
        existing.setPrices(new HashSet<>());

        for (Price p : product.getPrices()) {
            if (existing.getPrices().contains(p)) {
                existing.removePrice(p);
            }
        }

        for (Image i : product.getImages()) {
            if (existing.getImages().contains(i)) {
                existing.removeImage(i);
            }
        }

        if (product.getSKU() != null && !product.getSKU().equals(existing.getSKU())) existing.setSKU(product.getSKU());
        if (product.getQuantity() != null && !product.getQuantity().equals(existing.getQuantity())) existing.setQuantity(product.getQuantity());
        if (product.getName() != null && !product.getName().equals(existing.getName())) existing.setName(product.getName());
        if (product.getDescription() != null && !product.getDescription().equals(existing.getDescription())) existing.setDescription(product.getDescription());

        saveProduct(existing);

        return product;
    }

    public Optional<Product> getProduct(Long productId) {
        return productRepository.findById(productId);
    }

    public List<Product> getProductsById(List<Long> productIds) {
        List<Product> products = new ArrayList<>();
        for (Long id : productIds) {
            Optional<Product> product = getProduct(id);
            product.ifPresent(products::add);
        }
        return products;
    }

    @Transactional
    public void addQuantity(Product product, Long quantity) {
        if (quantity != null) {
            product.setQuantity(product.getQuantity() + quantity);
            updateProduct(product);
        }
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = getProduct(productId).orElse(null);
        if (product != null) {
            productRepository.delete(product);
        }
    }

    public Price createPrice(BigDecimal price, Date date) {
        return priceService.createPrice(price, date);
    }

    public Product addPrice(Product product, Price price) {
        if (price != null && !product.getPrices().contains(price)) {
            product.addPrice(price);

            return updateProduct(product);
        }
        return product;
    }

    public Price getCurrentPrice(Long productId) {
        return getProduct(productId)
                .flatMap(product -> product.getPrices().stream().max(Comparator.comparing(Price::getDate)))
                .orElse(null);
    }

    public Price updatePrice(Price price) {
        return priceService.updatePrice(price);
    }

    public void deletePrice(Long priceId) {
        priceService.deletePrice(priceId);
    }

    public Image createImage(String name, String ref) {
        return imageService.createImage(name, ref);
    }

    public Product addImage(Product product, Image image) {
        if (image != null && !product.getImages().contains(image)) {
            product.addImage(image);

            return updateProduct(product);
        }
        return product;
    }

    public Image updateImage(Image image) {
        return imageService.updateImage(image);
    }

    public void deleteImage(Long imageId) {
        imageService.deleteImage(imageId);
    }
}