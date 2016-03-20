package pl.edu.agh.io.wishlist.persistence.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.persistence.UserDAO;
import pl.edu.agh.io.wishlist.domain.User;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class UserDAOImpl implements UserDAO {

    @Autowired
    DataSource dataSource;

    Connection conn = null;

    public void addUser(User user) {

    }

    public User getUser(String username) {
        return null;
    }
}
