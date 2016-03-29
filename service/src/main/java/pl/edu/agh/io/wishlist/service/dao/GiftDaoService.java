package pl.edu.agh.io.wishlist.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.persistence.dao.GiftDAO;
import pl.edu.agh.io.wishlist.service.IGiftService;

import java.util.List;


public class GiftDaoService implements IGiftService {
    @Autowired
    GiftDAO giftDAO;


    @Override
    public Gift getGift(Long id) {
        return giftDAO.get(id);
    }

    @Override
    public List<Gift> getAllGifts(Long id) {
        return giftDAO.getAll(id);
    }

    @Override
    public boolean addGift(Long userId, Gift gift) {
        giftDAO.put(gift);
        return true;
    }

    @Override
    public boolean removeGift(Long userId, Long giftId) {
        giftDAO.remove(giftId);
        return true;
    }

    @Override
    public boolean editGift(Long id, Gift gift) {
        return giftDAO.update(id, gift);
    }
}
