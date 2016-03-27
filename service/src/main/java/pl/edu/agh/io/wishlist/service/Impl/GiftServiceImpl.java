package pl.edu.agh.io.wishlist.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.persistence.GiftDAO;
import pl.edu.agh.io.wishlist.service.GiftService;

import java.util.List;

@Service("giftService")
public class GiftServiceImpl implements GiftService {
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
    public boolean addGift(Gift gift) {
        return giftDAO.insert(gift);
    }

    @Override
    public boolean removeGift(Long id) {
        return giftDAO.remove(id);
    }

    @Override
    public boolean editGift(Long id, String name, String description) {
        return giftDAO.update(id, name, description);
    }
}
