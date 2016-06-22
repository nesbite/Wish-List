package pl.edu.agh.io.wishlist.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.request.RequestContextListener;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.domain.Role;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.GiftRepository;
import pl.edu.agh.io.wishlist.persistence.RoleRepository;
import pl.edu.agh.io.wishlist.persistence.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@ImportResource("classpath:pl/edu/agh/io/wishlist/server/beans.xml")
public class Application extends SpringBootServletInitializer implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GiftRepository giftRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Override
    public void run(String... strings) throws Exception {
        Role userRole = roleRepository.findByName("ROLE_USER");

        userRepository.deleteAll();
        giftRepository.deleteAll();

        List<Gift> gifts1 = createDummyGifts(4, "one");
        List<Gift> gifts2 = createDummyGifts(2, "two");
        List<Gift> gifts3 = createDummyGifts(3, "three");

        gifts1 = giftRepository.save(gifts1);
        gifts2 = giftRepository.save(gifts2);
        gifts3 = giftRepository.save(gifts3);

        User user1 = createDummyUser("jarek", "abc123", userRole);
        User user2 = createDummyUser("janek", "pass", userRole);
        User user3 = createDummyUser("adam", "pass", userRole);

        user1.getGifts().addAll(gifts1);
        user2.getGifts().addAll(gifts2);
        user3.getGifts().addAll(gifts3);
        user1.setEmail("siatek25@gmail.com");
        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);
        user3 = userRepository.save(user3);

        user1.getFriends().add(user2.getUsername());
        user1.getFriends().add(user3.getUsername());

        user2.getFriends().add(user1.getUsername());
        user3.getFriends().add(user1.getUsername());

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
    }

    private User createDummyUser(String username, String password, Role... roles) {
        User user = new User();
        user.setUsername(username);
        user.setFirstName(username + "-first");
        user.setLastName(username + "-last");
        user.setPassword(passwordEncoder.encode(password));
        user.setEmail(username + "@sample.com");
        user.setEnabled(true);
        user.setRoles(Arrays.asList(roles));
        return user;
    }

    private List<Gift> createDummyGifts(int count, String prefix) {
        List<Gift> gifts = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            String name = prefix + "#" + i;
            gifts.add(new Gift(name, "description of " + name));
        }
        return gifts;
    }
}
