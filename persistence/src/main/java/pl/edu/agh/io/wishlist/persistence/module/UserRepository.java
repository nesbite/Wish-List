package pl.edu.agh.io.wishlist.persistence.module;

import pl.edu.agh.io.wishlist.domain.module.User;

public interface UserRepository {

    void save(User user);

    boolean exists(String id);

    int count();

    void delete(String id);

    User find(String id);
}
