package pl.edu.agh.io.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.DatabaseConnector;
import pl.edu.agh.io.UserService;
import pl.edu.agh.io.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    DatabaseConnector connector;

    public void addUser(User user) {

    }

    public User getUser(String username) {
        User user = null;
        String query = "SELECT * FROM USERS WHERE UserID = " + username + ";";
        ResultSet rs = connector.executeQuery(query);
        try {
            rs.next();
            user = new User(rs.getLong(1), rs.getString(2), rs.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
