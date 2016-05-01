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
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.GiftRepository;
import pl.edu.agh.io.wishlist.persistence.UserRepository;

import static com.jayway.restassured.RestAssured.given;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ApplicationTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GiftRepository giftRepository;

    @Value("${local.server.port}")
    int port;

    @Before
    public void setUp() throws Exception {
        RestAssured.port = port;

        userRepository.deleteAll();
        giftRepository.deleteAll();

        User user1 = new User("janek", "pass");
        User user2 = new User("adam", "pass");

        userRepository.save(user1);
        userRepository.save(user2);

        Gift gift1 = new Gift("n1", "d1");
        Gift gift2 = new Gift("n2", "d2");
        Gift gift3 = new Gift("n3", "d3");
        Gift gift4 = new Gift("n4", "d4");
        Gift gift5 = new Gift("n5", "d5");
        Gift gift6 = new Gift("n6", "d6");
        Gift gift7 = new Gift("n7", "d7");
        Gift gift8 = new Gift("n8", "d8");
        Gift gift9 = new Gift("n9", "d9");

        giftRepository.save(gift1);
        giftRepository.save(gift2);

        user1.getGifts().add(gift1);
        user1.getGifts().add(gift2);
        user1.getGifts().add(gift3);
        user1.getGifts().add(gift4);
        user1.getGifts().add(gift5);
        user1.getGifts().add(gift6);
        user1.getGifts().add(gift7);
        user1.getGifts().add(gift8);
        user1.getGifts().add(gift9);
        user2.getGifts().add(gift1);

        user1.getFriends().add(user2.getUsername());
        user2.getFriends().add(user1.getUsername());

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @Test
    public void basicAuthentication() throws Exception {
        given().auth().basic("janek", "pass").expect().statusCode(200).when().get("/users");
    }

    @Test
    public void basicAuthFailure() throws Exception {
        given().auth().basic("janek", "incorrect").expect().statusCode(401).when().get("/users");
    }

    @Test
    public void basicAuthFailureUnknownName() throws Exception {
        given().auth().basic("incorrect", "pass").expect().statusCode(401).when().get("/users");
    }

    @Test
    public void testUsersGet() throws Exception {
        // @formatter:off
        given()
                .auth()
                .basic("janek", "pass").
        when().
                get("/users/janek").
        then().
                statusCode(HttpStatus.SC_OK).
                body("username", Matchers.is("janek"));
        // @formatter:on
    }

    // TODO write more tests here or create another, similar test-class
    // see: http://www.jayway.com/2014/07/04/integration-testing-a-spring-boot-application/

}
