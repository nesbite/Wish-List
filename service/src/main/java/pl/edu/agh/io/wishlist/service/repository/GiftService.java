package pl.edu.agh.io.wishlist.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.GiftRepository;
import pl.edu.agh.io.wishlist.persistence.UserRepository;
import pl.edu.agh.io.wishlist.service.IGiftService;

import java.util.List;

@Service
public class GiftService implements IGiftService {

    @Autowired
    GiftRepository giftRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Gift getGift(String id) {
        return giftRepository.findOne(id);
    }

    @Override
    public List<Gift> getAllGifts(String userId) {
        return userRepository.findOne(userId).getGifts();
    }

    @Override
    public boolean addGift(String userId, Gift gift) {
        if (!userRepository.exists(userId)) {
            return false;
        }


        User user = userRepository.findOne(userId);
        giftRepository.save(gift);
        user.getGifts().add(gift);
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean removeGift(String userId, String giftId) {
        if (!giftRepository.exists(giftId) || !userRepository.exists(userId)) {
            return false;
        }
        User user = userRepository.findOne(userId);
        Gift gift = giftRepository.findOne(giftId);

        List<Gift> giftIdList = user.getGifts();
        if (!giftIdList.contains(gift)) {
            return false;
        }
        giftIdList.remove(gift);
        userRepository.save(user);
        giftRepository.delete(gift);

        return true;
    }

    @Override
    public boolean editGift(String id, Gift gift) {
        if (!giftRepository.exists(id)) {
            return false;
        }

        Gift oldGift = giftRepository.findOne(id);
        oldGift.setName(gift.getName());
        oldGift.setDescription((gift.getDescription()));

        giftRepository.save(oldGift);

        return true;
    }
}
