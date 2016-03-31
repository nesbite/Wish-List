package pl.edu.agh.io.wishlist.persistence.dao;

import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.domain.UserDetails;

public interface UserDAO {
    boolean addUser(UserDetails user);
    User getUser(String login);
}
