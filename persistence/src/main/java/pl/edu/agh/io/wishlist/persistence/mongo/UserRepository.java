package pl.edu.agh.io.wishlist.persistence.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.agh.io.wishlist.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

    User findByLogin(String login);
}
