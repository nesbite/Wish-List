package pl.edu.agh.io.wishlist.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.service.IAuthService;
import pl.edu.agh.io.wishlist.service.IUserService;
import pl.edu.agh.io.wishlist.service.exceptions.AuthException;
import pl.edu.agh.io.wishlist.service.exceptions.UserNotFoundException;

@Service
public class AuthService implements IAuthService {

    @Autowired
    private IUserService userService;

    @Override
    public void login(String username, String password) {
        User user = userService.getUser(username);
        if (user == null) {
            throw new UserNotFoundException(username);
        }

        if (!user.getPassword().equals(password)) {
            throw new AuthException("wrong password");
        }
    }

    @Override
    public void register(String username, String password) {
        userService.addUser(new User(username, password));
    }
}
