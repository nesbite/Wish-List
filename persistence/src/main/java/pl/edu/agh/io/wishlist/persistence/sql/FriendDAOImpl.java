package pl.edu.agh.io.wishlist.persistence.sql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.FriendDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FriendDAOImpl implements FriendDAO {
    @Autowired
    private DataSource dataSource;

    private Connection conn = null;

    @Override
    public List<User> getAll(Long id) {
        List<User> friends = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT FriendID FROM wishlist.Friends WHERE UserID = ?");
            ps.setLong(1, id);
            ResultSet result = ps.executeQuery();
            Long friendId;
            String login, password;
            while(result.next()){
                friendId = result.getLong(1);
                PreparedStatement preparedStatement = conn.prepareStatement("SELECT Login, PasswordHash FROM Users WHERE UserID = ?");
                preparedStatement.setLong(1, friendId);
                ResultSet resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    login = resultSet.getString(1);
                    password = resultSet.getString(2);
                    User user = new User(id, login, password);
                    friends.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return friends;
    }

    @Override
    public boolean add(Long userId, Long friendId) {
        int result = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Friends VALUES (?, ?)");
            ps.setLong(1, userId);
            ps.setLong(2, friendId);
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

    @Override
    public boolean delete(Long userId, Long friendId) {
        int result = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Friends WHERE UserID = ? AND FriendID = ?");
            ps.setLong(1, userId);
            ps.setLong(2, friendId);
            result = ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result != -1;
    }
}
