package pl.edu.agh.io.Impl;

import com.sun.javafx.binding.StringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.DatabaseConnector;
import pl.edu.agh.io.Gift;
import pl.edu.agh.io.GiftService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Service
public class GiftServiceImpl implements GiftService {
    @Autowired
    DatabaseConnector dbc;

    public boolean addGift(Long id, Gift gift) {
        dbc.executeUpdate(String.format("INSERT INTO Gifts VALUES (null, \"%s\", \"%s\", %d)", gift.getName(), gift.getDescription(), id));
        return true;
    }

    public Gift getGift(Long id) {
        ResultSet rs = dbc.executeQuery(String.format("SELECT * FROM Gifts WHERE GiftID = %d", id));

        try {
            rs.next();
            System.out.println(rs.getLong("GiftID") + rs.getString("Name") + rs.getString("Description"));
            return new Gift(rs.getLong("GiftID"), rs.getString("Name"), rs.getString("Description"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Gift> getAllGifts() {
        List<Gift> giftList = new LinkedList<>();
        ResultSet rs = dbc.executeQuery("SELECT * FROM Gifts");
        try {
            while (rs.next()) {
                long giftID = rs.getLong("giftID");
                String name = rs.getString("name");
                String desc = rs.getString("description");
                long userID = rs.getLong("userid");

                //Assuming you have a user object
                Gift gift = new Gift(giftID, name, desc);
                giftList.add(gift);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return giftList;
    }

    public boolean removeGift(Long id) {
        dbc.executeUpdate(String.format("DELETE FROM Gifts WHERE GiftID = %d", id));
        return true;
    }

    public boolean updateGift(Long id, String name, String description) {
        dbc.executeUpdate(String.format("UPDATE Gifts SET Name=\"%s\", Description=\"%s\" WHERE GiftID = %d", name, description, id));
        return false;
    }
}
