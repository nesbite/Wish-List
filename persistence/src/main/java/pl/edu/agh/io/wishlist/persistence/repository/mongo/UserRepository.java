package pl.edu.agh.io.wishlist.persistence.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.io.wishlist.domain.User;

@Repository
public interface UserRepository extends MongoRepository<User, Long> {
    User findByLogin(String login);
}
