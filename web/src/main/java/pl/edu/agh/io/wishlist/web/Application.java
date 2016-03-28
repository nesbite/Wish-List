package pl.edu.agh.io.wishlist.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.persistence.GiftDAO;

@EnableAutoConfiguration
@Configuration
@ImportResource({"classpath:pl/edu/agh/io/wishlist/persistence/Spring-Module.xml"})
@ComponentScan(basePackages="pl.edu.agh.io.wishlist")
public class Application {


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

    }
}