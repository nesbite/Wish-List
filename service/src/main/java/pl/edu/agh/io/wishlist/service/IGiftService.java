package pl.edu.agh.io.wishlist.service;

import pl.edu.agh.io.wishlist.domain.Gift;

import java.util.List;

public interface IGiftService {
    Gift getGift(String id);
    List<Gift> getAllGifts(String id);
    boolean addGift(String userId, Gift gift);
    boolean removeGift(String userId, String giftId);
    boolean editGift(String id, Gift gift);
}
