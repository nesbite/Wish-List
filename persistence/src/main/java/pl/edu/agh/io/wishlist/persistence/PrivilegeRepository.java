package pl.edu.agh.io.wishlist.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.io.wishlist.domain.Privilege;

@Repository
public interface PrivilegeRepository extends MongoRepository<Privilege, String> {

    Privilege findByName(String name);

    @Override
    void delete(Privilege privilege);

}
