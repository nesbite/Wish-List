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
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.RoleRepository;
import pl.edu.agh.io.wishlist.persistence.UserRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.Arrays;

@SpringBootApplication
@ImportResource("classpath:pl/edu/agh/io/wishlist/server/beans.xml")
public class Application extends SpringBootServletInitializer implements CommandLineRunner{

    @Autowired
    UserRepository userRepository;

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
        userRepository.deleteAll();
        final User user = new User();
        user.setUsername("siatek25");
        user.setFirstName("jan");
        user.setLastName("kowalski");
        user.setPassword(passwordEncoder.encode("abc123"));
        user.setEmail("siatek25@gmail.com");
        user.setEnabled(true);
        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user);

        final User user2 = new User();
        user2.setUsername("sysunia991");
        user2.setFirstName("marek");
        user2.setLastName("nowak");
        user2.setPassword(passwordEncoder.encode("abcd1234"));
        user2.setEmail("sysunia991@gmail.com");
        user2.setEnabled(true);
        user2.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));

        user2.getFriends().add(userRepository.findByUsername(user.getUsername()).getId());
        userRepository.save(user2);
    }

//     @Override
//     public void onStartup(ServletContext servletContext) throws ServletException {
//     super.onStartup(servletContext);
//     servletContext.addListener(new RequestContextListener());
//     }
}
