package store.backend.database;

import org.apache.coyote.ProtocolHandler;
import store.backend.database.entity.*;

import java.net.MalformedURLException;
import java.net.ProtocolFamily;
import java.net.URL;
import java.util.Date;
import java.util.Random;

public class DBLoader {

    public DBLoader(){
        Image image1 = new Image();
        image1.setName("IPhone 15 1");
        try {
            image1.setReference(new URL(null,"https://www.google.com/search?q=iphone+15+1"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Image image2 = new Image();
        image2.setName("IPhone 15 2");
        try {
            image2.setReference(new URL(null,"https://www.google.com/search?q=iphone+15+2"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        Image image3 = new Image();
        image3.setName("IPhone 15 3");
        try {
            image3.setReference(new URL(null,"https://www.google.com/search?q=iphone+15+3"));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        Price price1 = new Price();
        price1.setDate(new Date());

        SKU sku1 = new SKU();
        sku1.setSku("asd1");

        SKU sku2 = new SKU();
        sku2.setSku("asd2");

        SKU sku3 = new SKU();
        sku3.setSku("asd3");

        SKU sku4 = new SKU();
        sku4.setId((new Random()).nextLong());
        sku4.setSku("asd4");

        Product product1 = new Product();
        product1.setName("IPhone 15");
        product1.addImage(image1);
        product1.addImage(image2);
        product1.addImage(image3);
        product1.addPrice(price1);
        product1.addSKU(sku1);
        product1.addSKU(sku2);
        product1.addSKU(sku3);
        product1.addSKU(sku4);

        Category innerCategory = new Category();
        innerCategory.setName("inner");
        innerCategory.addProduct(product1);
//
//        Category outerCategory = new Category();
//        outerCategory.setName("outer");
//        outerCategory.addCategory(innerCategory);
    }
}
