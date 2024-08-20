package store.backend.service.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.backend.database.entity.*;
import store.backend.database.repository.ProductRepository;

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

    public Product createProduct(
            String name,
            String description,
            Category category
    ) {
        Product product = Product.builder()
                .name(name)
                .description(description)
                .build();

        category.addProduct(product);

        return product;
    }

    public Price addPrice(Product product, Price price) {
        return priceService.addPrice(product, price);
    }

    public Image addImage(Product product, Image image) {
        return imageService.addImage(product, image);
    }

    public SKU addSKU(Product product, SKU sku) {
        return skuService.addSKU(product, sku);
    }
}
