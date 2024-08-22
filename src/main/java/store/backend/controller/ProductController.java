package store.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import store.backend.database.entity.Image;
import store.backend.database.entity.Price;
import store.backend.database.entity.Product;
import store.backend.database.entity.SKU;
import store.backend.database.repository.ProductRepository;
import store.backend.service.product.ProductService;

import java.math.BigDecimal;
import java.util.Date;
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
        return productService.getAll();
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
    public Image addImage(@PathVariable("id") Long product_id, Image image) {
        return productService.addImage(product_id, image);
    }

    @PutMapping("/update/image/{id}")
    public Image updateImage(@PathVariable("id") Long image_id, Image image) {
        return productService.updateImage(image_id, image);
    }

    @DeleteMapping("/{id}/delete/image")
    public void deleteImage(@PathVariable("id") Long product_id, @RequestParam Long image_id) {
        productService.deleteImage(product_id, image_id);
    }

//    Price

    @PostMapping("/{id}/create/price")
    public Price createPrice(BigDecimal price, Date date) {
        return productService.createPrice(price, date);
    }

    @PostMapping("/{id}/add/price")
    public Price addPrice(@PathVariable("id") Long product_id, Price price) {
        return productService.addPrice(product_id, price);
    }

    @PutMapping("/update/price/{id}")
    public Price updatePrice(@PathVariable("id") Long price_id, Price price) {
        return productService.updatePrice(price_id, price);
    }

    @DeleteMapping("/{id}/delete/price")
    public void deletePrice(@PathVariable("id") Long product_id, @RequestParam Long price_id) {
        productService.deletePrice(product_id, price_id);
    }
//    SKU

    @GetMapping("/{id}/available")
    public Boolean isAvailable(@PathVariable("id") Long product_id) {
        for (SKU sku : productService.getAvailableSKU(product_id)) {
            return true;
        }

        return false;
    }

    @PostMapping("/{id}/create/sku")
    public SKU createSKU(String sku) {
        return productService.createSKU(sku);
    }

    @PostMapping("/{id}/add/sku")
    public SKU addSKU(@PathVariable("id") Long product_id, SKU sku) {
        return productService.addSKU(product_id, sku);
    }


    @PutMapping("/update/sku/{id}")
    public SKU updateSKU(@PathVariable("id") Long sku_id, SKU sku) {
        return productService.updateSKU(sku_id, sku);
    }

    @DeleteMapping("/{id}/delete/sku")
    public void deleteSKU(@PathVariable("id") Long product_id, @RequestParam Long sku_id) {
        productService.deleteSKU(product_id, sku_id);
    }
}
