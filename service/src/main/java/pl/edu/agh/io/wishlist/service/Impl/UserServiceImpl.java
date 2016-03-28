package pl.edu.agh.io.wishlist.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.dao.UserDAO;
import pl.edu.agh.io.wishlist.service.UserService;
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

    @Override
    public boolean addUser(User user) {
        return userDAO.addUser(user);
    }

    @Override
    public User getUser(String login) {
        return userDAO.getUser(login);
    }
}
