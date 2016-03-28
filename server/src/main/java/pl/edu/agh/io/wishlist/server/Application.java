package pl.edu.agh.io.wishlist.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@EnableAutoConfiguration
@Configuration
@ImportResource({"classpath:pl/edu/agh/io/wishlist/persistence/beans-persistence.xml"})
@ComponentScan(basePackages = "pl.edu.agh.io.wishlist")
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
}