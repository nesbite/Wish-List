package pl.edu.agh.io.wishlist.persistence.module;

import pl.edu.agh.io.wishlist.domain.module.UserDetails;

public interface UserDetailsRepository {

    void save(UserDetails userDetails);

    boolean exists(String id);

    int count();

    void delete(String id);

    UserDetails find(String id);

}
