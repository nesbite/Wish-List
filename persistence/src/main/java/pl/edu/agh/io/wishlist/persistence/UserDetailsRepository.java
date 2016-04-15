package pl.edu.agh.io.wishlist.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.io.wishlist.domain.UserDetails;

@Repository
public interface UserDetailsRepository extends MongoRepository<UserDetails, String> {
    UserDetails findByUsername(String username);
    UserDetails findByUsernameAndPassword(String username, String password);
}
