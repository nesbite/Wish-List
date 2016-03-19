package pl.edu.agh.io;

import java.util.List;

public interface GiftService {
    boolean addGift(Gift gift);
    Gift getGift(Long id);
    List<Gift> getAllGifts();
    boolean removeGift(Long id);
    boolean updateGift(Long id, String name, String description);
}
