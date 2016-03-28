package pl.edu.agh.io.wishlist.persistence.sql;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.UserDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {

    @Autowired
    DataSource dataSource;

    private Connection conn = null;

    public boolean addUser(User user) {
        int result = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Users VALUES (null, ?, ?)");
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
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
        try{
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE login = ?");
            ps.setString(1, login);
            ResultSet result = ps.executeQuery();
            Long id = result.getLong(1);
            String password = result.getString(3);
            user = new User(login, password);
            user.setId(id);
            result.close();
            ps.close();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
            if (conn != null){
                try{
                    conn.close();
                } catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

        return user;
    }
}
