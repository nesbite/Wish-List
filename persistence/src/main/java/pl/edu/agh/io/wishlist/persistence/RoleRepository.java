package pl.edu.agh.io.wishlist.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.edu.agh.io.wishlist.domain.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    Role findByName(String name);

    @Override
    void delete(Role role);

}
