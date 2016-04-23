package pl.edu.agh.io.wishlist.service;

import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.service.exceptions.UserNotFoundException;

import java.util.Collection;

public interface IUserService {
    void addUser(User user);

    User getUser(String username);

    Collection<User> getUsers();

    void update(User user) throws UserNotFoundException;
}
