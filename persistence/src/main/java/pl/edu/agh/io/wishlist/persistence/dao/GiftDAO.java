package pl.edu.agh.io.wishlist.persistence.dao;

import pl.edu.agh.io.wishlist.domain.Gift;

import java.util.List;

public interface GiftDAO {
    Gift get(Long giftID);

    List<Gift> getAll(Long userID);

    boolean put(Gift gift);

    boolean remove(Long giftID);

    boolean update(Long giftID, Gift gift);
}
