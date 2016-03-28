package pl.edu.agh.io.wishlist.persistence.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.agh.io.wishlist.domain.User;

public interface UserRepository extends MongoRepository<User, Long> {
    User findByLogin(String login);
}
