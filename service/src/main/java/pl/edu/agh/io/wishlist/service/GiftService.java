package pl.edu.agh.io.wishlist.service;

import pl.edu.agh.io.wishlist.domain.Gift;

import java.util.List;

public interface GiftService {
    Gift getGift(Long id);
    List<Gift> getAllGifts(Long id);
    boolean addGift(Gift gift);
    boolean removeGift(Long id);
    boolean editGift(Long id, Gift gift);
}
