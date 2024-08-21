package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.*;
import store.backend.database.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private PriceService priceService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private SKUService skuService;

    public Product createProduct(String name, String description, Category category) {
        Product product = Product.builder()
                .name(name)
                .description(description)
                .build();

        category.addProduct(product);

        return product;
    }

    public Product updateProduct(Long product_id, Product editedProduct) {
        return productRepository
                .findById(product_id)
                .map(
                    product -> {
                        product.setCategory(editedProduct.getCategory());
                        product.setName(editedProduct.getName());
                        product.setPrices(editedProduct.getPrices());
                        product.setImages(editedProduct.getImages());
                        product.setSkus(editedProduct.getSkus());
                        product.setDescription(editedProduct.getDescription());

                        return productRepository.save(product);
                    }
                ).orElse(null);
    }

    public Optional<Product> getProduct(Long product_id) {
        return productRepository.findById(product_id);
    }

    public Iterable<Product> getAll() {
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

    public Price addPrice(Long product_id, Price price) {
        return productRepository.findById(product_id).map(product -> priceService.addPrice(product, price)).orElse(null);
    }

    public Price updatePrice(Long price_id, Price editedPrice) {
        return priceService.updatePrice(price_id, editedPrice);
    }

    public Image createImage(String name, String ref) {
        return imageService.createImage(name, ref);
    }

    public Image addImage(Long product_id, Image image) {
        return productRepository.findById(product_id).map(product -> imageService.addImage(product, image)).orElse(null);
    }

    public Image updateImage(Long image_id, Image editedImage) {
        return imageService.updateImage(image_id, editedImage);
    }

    public SKU createSKU(String sku) {
        return skuService.createSKU(sku);
    }

    public SKU addSKU(Long product_id, SKU sku) {
        return productRepository.findById(product_id).map(product -> skuService.addSKU(product, sku)).orElse(null);
    }

    public SKU updateSKU(Long sku_id, SKU editedSKU) {
        return skuService.updateSKU(sku_id, editedSKU);
    }
}
