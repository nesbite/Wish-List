package pl.edu.agh.io.wishlist.persistence.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.io.wishlist.domain.Gift;

@Repository
public interface GiftRepository extends MongoRepository<Gift, Long> {

}
