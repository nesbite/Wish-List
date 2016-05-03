package pl.edu.agh.io.wishlist.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.domain.VerificationToken;

@Repository
public interface VerificationTokenRepository extends MongoRepository<VerificationToken, String> {

    VerificationToken findByToken(String token);

    VerificationToken findByUser(User user);

}
