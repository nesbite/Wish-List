package pl.edu.agh.io.wishlist.persistence.dao.sql;

import org.springframework.beans.factory.annotation.Autowired;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.persistence.dao.GiftDAO;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class GiftSqlDAO implements GiftDAO {
    @Autowired
    private DataSource dataSource;

    private Connection conn = null;

    public boolean put(Gift gift) {
        int result = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Gifts VALUES (null, ?, ?, ?)");
            ps.setString(1, gift.getName());
            ps.setString(2, gift.getDescription());
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

    public Gift get(Long giftID) {
        Gift gift = null;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Gifts WHERE GiftID = ?");
            ps.setLong(1, giftID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("giftID");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                long userID = rs.getLong("userID");
                gift = new Gift(name, desc);
            }
            rs.close();
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
        return gift;
    }

    public List<Gift> getAll(Long userID) {
        List<Gift> giftList = new ArrayList<>();
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Gifts WHERE UserID = ?");
            ps.setLong(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                long giftID = rs.getLong("giftID");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                long UID = rs.getLong("giftID");
                Gift gift = new Gift(name, desc);
                giftList.add(gift);
            }
            rs.close();
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
        return giftList;
    }

    public boolean remove(Long giftID) {
        int result = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Gifts WHERE GiftID = ?");
            ps.setLong(1, giftID);
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

    public boolean update(Long giftID, Gift gift) {
        int result = -1;
        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("UPDATE Gifts SET Name=?, Description=? WHERE GiftID = ?");
            ps.setString(1, gift.getName());
            ps.setString(2, gift.getDescription());
            ps.setLong(3, giftID);
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

}
