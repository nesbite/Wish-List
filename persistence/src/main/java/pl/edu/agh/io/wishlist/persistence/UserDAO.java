package pl.edu.agh.io.wishlist.persistence;

import pl.edu.agh.io.wishlist.domain.User;

public interface UserDAO {
    boolean addUser(User user);
    User getUser(String username);
}
