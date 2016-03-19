package pl.edu.agh.io.Impl;

import com.sun.javafx.binding.StringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.DatabaseConnector;
import pl.edu.agh.io.Gift;
import pl.edu.agh.io.GiftService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class GiftServiceImpl implements GiftService {
    @Autowired
    DatabaseConnector dbc;

    public boolean addGift(Gift gift) {
        dbc.executeUpdate("INSERT INTO wishlist.presents VALUES (2, \"alalalalla\", \"description\", 1)");
        return true;
    }

    public Gift getGift(Long id) {
        ResultSet rs = dbc.executeQuery(String.format("SELECT * FROM presents WHERE PresentID = %d", id));

        try {
            rs.next();
            System.out.println(rs.getLong("PresentID") + rs.getString("Name") + rs.getString("Description"));
            return new Gift(rs.getLong("PresentID"), rs.getString("Name"), rs.getString("Description"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Gift> getAllGifts() {
        return null;
    }

    public boolean removeGift(Long id) {
        return false;
    }

    public boolean updateGift(Long id, String name, String description) {
        return false;
    }
}
