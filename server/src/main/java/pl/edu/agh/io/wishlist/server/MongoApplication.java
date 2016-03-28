package pl.edu.agh.io.wishlist.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.repository.mongo.UserRepository;

//Execute this in mongo shell before running the application:
//db.sequence.insert({_id: "userID",seq: 0})


@SpringBootApplication
@ImportResource("classpath:pl/edu/agh/io/wishlist/server/beans.xml")
@ComponentScan(basePackages = "pl.edu.agh.io.wishlist")
public class MongoApplication implements CommandLineRunner {

    @Autowired
    private UserRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(MongoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

//		repository.deleteAll();

        // save a couple of customers
//        repository.save(new User("Alice", "Smith"));
//        repository.save(new User("Bob", "Smith"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (User user : repository.findAll()) {
            System.out.println(user);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(repository.findByLogin("Alice"));

    }

}
