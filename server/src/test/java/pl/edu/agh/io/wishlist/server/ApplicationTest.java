package pl.edu.agh.io.wishlist.server;

import com.jayway.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.UserRepository;

import static com.jayway.restassured.RestAssured.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ApplicationTest {

    @Autowired
    private UserRepository repository;

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = port;

        User user = new User("jan");

        repository.deleteAll();
        repository.save(user);
    }

    @Test
    public void testUsersGet() throws Exception {
        when().
                get("/users/jan").
                then().
                statusCode(HttpStatus.SC_OK).
                body("login", Matchers.is("jan")).
                body("password", Matchers.is("pass_jan"));
    }

    // TODO write more tests here or create another, similar test-class
    // see: http://www.jayway.com/2014/07/04/integration-testing-a-spring-boot-application/

}