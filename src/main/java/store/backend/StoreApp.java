package store.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import store.backend.controller.ProductController;
import store.backend.database.DBLoader;

@SpringBootApplication
@EnableJpaRepositories
public class StoreApp {

	public static void main(String[] args) {

		SpringApplication.run(StoreApp.class, args);

		ApplicationContext context = new AnnotationConfigApplicationContext(StoreApp.class);
		ProductController productController = context.getBean(ProductController.class);
		System.out.println(productController.getAllProducts());
	}

	@Bean
	public DBLoader load() {
		return new DBLoader();
	}

}
