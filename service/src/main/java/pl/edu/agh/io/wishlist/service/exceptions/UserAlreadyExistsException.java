package pl.edu.agh.io.wishlist.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.edu.agh.io.wishlist.domain.User;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(User user) {
        super("user already exists: " + user.getUsername());
    }
}
