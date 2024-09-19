package store.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.Image;
import store.backend.database.entity.Price;
import store.backend.database.entity.Product;
import store.backend.service.product.ProductService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    //  Product
    @GetMapping("/{id}")
    public Optional<Product> getProduct(@PathVariable("id") Long id) {
        return productService.getProduct(id);
    }

    @GetMapping("/all")
    public Iterable<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/all/a")
    public Iterable<Product> getAllProductsById(List<Long> products_id) {
        return productService.getProductsById(products_id);
    }

    @GetMapping("/{id}/quantity")
    public Long getQuantity(@PathVariable("id") Long product_id) {
        return productService.getProduct(product_id).get().getQuantity();
    }

    @PutMapping("/{id}/quantity")
    public Long addQuantity(@PathVariable("id") Long product_id, @RequestParam Long count) {
        return getProduct(product_id).map(
                product -> {
                    productService.addQuantity(product, count);

                    return product.getQuantity();
                }
        ).orElse(0L);
    }

    @PostMapping
    public Product postProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public Product putProduct(@PathVariable Long id, @RequestBody Product product) {
        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
    }


//    Image

    @PostMapping("/{id}/create/image")
    public Image createImage(String name, String ref) {
        return productService.createImage(name, ref);
    }

    @PostMapping("/{id}/add/image")
    public void addImage(@PathVariable("id") Long product_id, Image image) {
        getProduct(product_id).ifPresent(p -> p.addImage(image));
    }

    @PutMapping("/update/image/{id}")
    public Image updateImage(@PathVariable("id") Long image_id, Image image) {
        return productService.updateImage(image_id, image);
    }

    @DeleteMapping("/{id}/delete/image")
    public void deleteImage(@RequestParam Long image_id) {
        productService.deleteImage(image_id);
    }

//    Price

    @PostMapping("/{id}/create/price")
    public Price createPrice(BigDecimal price, Date date) {
        return productService.createPrice(price, date);
    }

    @PostMapping("/{id}/add/price")
    public void addPrice(@PathVariable("id") Long product_id, Price price) {
        productService.getProduct(product_id).ifPresent(p -> p.addPrice(price));
    }

    @PutMapping("/update/price/{id}")
    public Price updatePrice(@PathVariable("id") Long price_id, Price price) {
        return productService.updatePrice(price_id, price);
    }

    @DeleteMapping("/{id}/delete/price")
    public void deletePrice(@RequestParam Long price_id) {
        productService.deletePrice(price_id);
    }
}
