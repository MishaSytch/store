package store.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Configuration
@ComponentScan(basePackages = {"store/backend"})
@EnableTransactionManagement
public class MainConfig {
//
//    @Bean
//    public EntityManager getEntityManager() {
//        return Persistence.createEntityManagerFactory("Store").createEntityManager();
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new DataSourceTransactionManager();
//    }
}
