package pl.edu.agh.io.wishlist.service;

import pl.edu.agh.io.wishlist.domain.User;

public interface UserService {
    boolean addUser(User user);
    User getUser(String login);
}
