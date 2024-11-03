package store.backend.service.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import store.backend.database.entity.Image;
import store.backend.database.entity.Price;
import store.backend.database.entity.Product;
import store.backend.database.loader.DBLoader;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Date;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private DBLoader dbLoader;

    @Autowired
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dbLoader.delete();
    }

    @Test
    void testCreateProduct() {
        Product createdProduct = productService.createProduct("name", "description", "sku", 10L);

        assertNotNull(productService.getProduct(createdProduct.getId()).get());
    }

    @Test
    void testSaveProduct() {
        Product createdProduct = productService.createProduct("name", "description", "sku", 10L);
        dbLoader.delete();

        assertNotNull(productService.getProduct(productService.saveProduct(createdProduct).getId()).get());

    }

    @Test
    void testUpdateProduct() {
        Product createdProduct = productService.createProduct("name", "description", "sku", 10L);

        createdProduct.setQuantity(99L);
        createdProduct.setName("updated name");
        productService.updateProduct(createdProduct);

        assertNotNull(productService.getProduct(createdProduct.getId()).get());
        assertEquals(createdProduct.getName(), productService.getProduct(createdProduct.getId()).get().getName());
        assertEquals(createdProduct.getQuantity(), productService.getProduct(createdProduct.getId()).get().getQuantity());
    }

    @Test
    void testGetProduct() {
        Product createdProduct = productService.createProduct("name", "description", "sku", 10L);

        assertEquals(createdProduct.getName(), productService.getProduct(createdProduct.getId()).get().getName());
    }

    @Test
    void testGetProductsById() {
        Product createdProduct = productService.createProduct("name", "description", "sku", 10L);

        List<Product> products = productService.getProductsById(Collections.singletonList(createdProduct.getId()));

        assertEquals(1, products.size());
        assertEquals(createdProduct.getName(), products.get(0).getName());
    }

    @Test
    void testAddQuantity() {
        Product createdProduct = productService.createProduct("name", "description", "sku", 10L);
        productService.addQuantity(createdProduct, 5L);

        assertEquals(15L, createdProduct.getQuantity());
    }

    @Test
    void testGetAllProducts() {
        dbLoader.delete();
        Product createdProduct = productService.createProduct("name", "description", "sku", 10L);

        List<Product> allProducts = productService.getAllProducts();

        assertEquals(createdProduct.getName(), allProducts.get(0).getName());
    }

    @Test
    void testDeleteProduct() {
        Product createdProduct = productService.createProduct("name", "description", "sku", 10L);

        productService.deleteProduct(createdProduct.getId());

        assertThrows(NoSuchElementException.class, () -> productService.getProduct(createdProduct.getId()).get());
    }

    @Test
    void testAddPrice() {
        Product createdProduct = productService.createProduct("name", "description", "sku", 10L);
        Price price = productService.createPrice(new BigDecimal(10), new Date());

        productService.addPrice(createdProduct, price);

        assertTrue(createdProduct.getPrices().contains(price));
    }

    @Test
    void testGetCurrentPrice() {
        Product createdProduct = productService.createProduct("name", "description", "sku", 10L);
        Price price = productService.createPrice(new BigDecimal(10), new Date());
        Price newPrice = productService.createPrice(new BigDecimal(10), new Date());

        productService.addPrice(createdProduct, newPrice);
        productService.addPrice(createdProduct, price);

        assertEquals(newPrice.getDate(), productService.getCurrentPrice(createdProduct.getId()).getDate());

    }

    @Test
    void testUpdatePrice() {
        Product createdProduct = productService.createProduct("name", "description", "sku", 10L);
        Price price = productService.createPrice(new BigDecimal(10), new Date());

        productService.addPrice(createdProduct, price);

        price.setDate(new Date());
        productService.updatePrice(price);

        assertEquals(price.getDate(), productService.getCurrentPrice(createdProduct.getId()).getDate());
    }

    @Test
    void testDeletePrice() {
        dbLoader.delete();
        Product createdProduct = productService.createProduct("name", "description", "sku", 10L);
        Price price = productService.createPrice(new BigDecimal(10), new Date());

        productService.addPrice(createdProduct, price);

        productService.deletePrice(price.getId());

        assertFalse(productService.getProduct(createdProduct.getId()).get().getPrices().contains(price));
    }

    @Test
    void testAddImage() {
        Product product = productService.createProduct("test", "desc", "sku", 100L);
        Image image = productService.createImage("name", "ref");

        productService.addImage(product, image);

        assertTrue(product.getImages().contains(image));
    }

    @Test
    void testUpdateImage() {
        Image image = productService.createImage("name", "ref");
        image.setName("new name");

        Image updatedImage = productService.updateImage(image);

        assertEquals(image.getName(), updatedImage.getName());
    }

    @Test
    void testDeleteImage() {
        dbLoader.delete();
        Product createdProduct = productService.createProduct("name", "description", "sku", 10L);
        Image image = productService.createImage("name", "ref");

        productService.addImage(createdProduct, image);

        productService.deleteImage(image.getId());

        assertFalse(productService.getProduct(createdProduct.getId()).get().getPrices().contains(image));
    }
}