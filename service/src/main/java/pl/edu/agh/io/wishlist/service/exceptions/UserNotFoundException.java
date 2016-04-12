package pl.edu.agh.io.wishlist.service.exceptions;

import pl.edu.agh.io.wishlist.domain.User;

public class UserNotFoundException extends Exception {

    public UserNotFoundException(User user) {
        super("user does not exist: " + user.getUsername());
    }
}
