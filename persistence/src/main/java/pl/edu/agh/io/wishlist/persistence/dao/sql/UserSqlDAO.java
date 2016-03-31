package pl.edu.agh.io.wishlist.persistence.dao.sql;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.domain.UserDetails;
import pl.edu.agh.io.wishlist.persistence.dao.UserDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSqlDAO implements UserDAO {

    @Autowired
    DataSource dataSource;

    private Connection conn = null;

    public boolean addUser(UserDetails user) {
        int result = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Users VALUES (null, ?)");
            ps.setString(1, user.getUsername());
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return result != -1;
    }

    public User getUser(String login) {
        User user = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE login = ?");
            ps.setString(1, login);
            ResultSet result = ps.executeQuery();
            Long id = result.getLong(1);
            user = new User(login);
            result.close();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return user;
    }
}
