package pl.edu.agh.io.wishlist.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.edu.agh.io.wishlist.persistence.UserDetailsRepository;

@SpringBootApplication
@ImportResource("classpath:pl/edu/agh/io/wishlist/server/beans.xml")
@ComponentScan(basePackages = "pl.edu.agh.io.wishlist")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}

//@Configuration
//class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {
//
//    @Autowired
//    UserDetailsRepository userDetailsRepository;
//
//    @Override
//    public void init(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService());
//    }
//
//    @Bean
//    UserDetailsService userDetailsService() {
//        return new UserDetailsService() {
//
//            @Override
//            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//                pl.edu.agh.io.wishlist.domain.UserDetails user = userDetailsRepository.findByUsername(username);
//                if(user != null) {
//                    return new User(user.getUsername(), user.getPassword(), true, true, true, true,
//                            user.getRoles());
//                } else {
//                    throw new UsernameNotFoundException("could not find the user '"
//                            + username + "'");
//                }
//            }
//
//        };
//    }
//}
//
//@EnableWebSecurity
//@Configuration
//class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().fullyAuthenticated().and().
//                httpBasic().and().
//                csrf().disable();
//    }
//
//}
