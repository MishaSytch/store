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
        return productRepository.findById(product_id)
                .map(
                        product -> {
                        product.addPrice(price);
                        return price;
                    }
        ).orElse(null);
    }

    public Price getPrice(Long product_id, Long price_id) {
        Iterable<Price> prices = productRepository.findAllPricesByProduct_id(product_id);
        for (Price price : prices) {
            if (price.getId().equals(price_id)) return price;
        }

        return null;
    }

    public Price getCurrentPrice(Long product_id) {
        return productRepository.findFirstPriceByProduct_idOrderByDate(product_id).orElse(null);
    }

    public Price updatePrice(Long price_id, Price editedPrice) {
        return priceService.updatePrice(price_id, editedPrice);
    }

    public void deletePrice(Long product_id, Long price_id) {
        if (getPrice(product_id, price_id) != null) priceService.deletePrice(price_id);
    }

    public Image createImage(String name, String ref) {
        return imageService.createImage(name, ref);
    }

    public Image addImage(Long product_id, Image image) {
        return productRepository.findById(product_id)
                .map(
                        product -> {
                            product.addImage(image);
                            return image;
                        }
                ).orElse(null);
    }

    public Image getImage(Long product_id, Long image_id) {
        Iterable<Image> images = productRepository.findAllImagesByProduct_id(product_id);
        for (Image image : images) {
            if (image.getId().equals(image_id)) return image;
        }

        return null;
    }

    public Image updateImage(Long image_id, Image editedImage) {
        return imageService.updateImage(image_id, editedImage);
    }

    public void deleteImage(Long product_id, Long image_id) {
        if (getImage(product_id, image_id) != null) imageService.deleteImage(image_id);
    }

    public Iterable<SKU> getAvailableSKU(Long product_id) {
        return skuService.getAvailableSKU(product_id);
    }

    public SKU createSKU(String sku) {
        return skuService.createSKU(sku);
    }

    public SKU addSKU(Long product_id, SKU sku) {
        return productRepository.findById(product_id)
                .map(
                        product -> {
                            product.addSKU(sku);
                            return sku;
                        }
                ).orElse(null);
    }

    public SKU getSKU(Long product_id, Long sku_id) {
        Iterable<SKU> skus = productRepository.findAllSKUsByProduct_id(product_id);
        for (SKU sku : skus) {
            if (sku.getId().equals(sku_id)) return sku;
        }

        return null;
    }

    public SKU updateSKU(Long sku_id, SKU editedSKU) {
        return skuService.updateSKU(sku_id, editedSKU);
    }

    public void deleteSKU(Long product_id, Long sku_id) {
        if (getSKU(product_id, sku_id) != null) skuService.deleteSKU(sku_id);
    }
}
