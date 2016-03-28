package pl.edu.agh.io.wishlist.service;

import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.User;

@Service("userService")
public interface UserService {
    boolean addUser(User user);

    User getUser(String login);
}
