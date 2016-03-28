package pl.edu.agh.io.wishlist.persistence.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.edu.agh.io.wishlist.domain.Gift;

public interface GiftRepository extends MongoRepository<Gift, Long> {



}
