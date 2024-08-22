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
                        product.setSKU(editedProduct.getSKU());
                        product.setDescription(editedProduct.getDescription());

                        return productRepository.save(product);
                    }
                ).orElse(null);
    }

    public Optional<Product> getProduct(Long product_id) {
        return productRepository.findById(product_id);
    }

    public void addQuantity(Long product_id, Long quantity) {
        getProduct(product_id).ifPresent(product -> product.setQuantity(product.getQuantity() + quantity));
    }

    public Long getQuantity(Long product_id) {
        return getProduct(product_id).map(Product::getQuantity).orElse(null);
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
        Optional<Product> product = getProduct(product_id);

        if (product.isPresent()) {
            for (Price price : product.get().getPrices()) {
                if (price.getId().equals(price_id)) return price;
            }
        }
        return null;
    }

    public Price getCurrentPrice(Long product_id) {
        return productRepository.findCurrentPriceByProduct_id(product_id).orElse(null);
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
        Optional<Product> product = getProduct(product_id);

        if (product.isPresent()) {
            for (Image image : product.get().getImages()) {
                if (image.getId().equals(image_id)) return image;
            }
        }
        return null;
    }

    public Image updateImage(Long image_id, Image editedImage) {
        return imageService.updateImage(image_id, editedImage);
    }

    public void deleteImage(Long product_id, Long image_id) {
        if (getImage(product_id, image_id) != null) imageService.deleteImage(image_id);
    }
}
