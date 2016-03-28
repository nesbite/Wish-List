package pl.edu.agh.io.wishlist.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.repository.mongo.GiftRepository;
import pl.edu.agh.io.wishlist.persistence.repository.mongo.UserRepository;
import pl.edu.agh.io.wishlist.service.IGiftService;

import java.util.List;

@Service("giftRepoService")
public class GiftRepoService implements IGiftService {

    @Autowired
    GiftRepository giftRepository;

    @Autowired
    UserRepository userRepository;


    @Override
    public Gift getGift(Long id) {
        return giftRepository.findOne(id);
    }

    @Override
    public List<Gift> getAllGifts(Long userId) {
        return userRepository.findOne(userId).getGifts();
    }

    @Override
    public boolean addGift(Long userId, Gift gift) {
        if (!userRepository.exists(userId)) {
            return false;
        }

        User user = userRepository.findOne(userId);
        user.getGifts().add(gift);

        userRepository.save(user);

        return true;
    }

    @Override
    public boolean removeGift(Long id) {
        if (!giftRepository.exists(id)) {
            return false;
        }

        giftRepository.delete(id);

        return true;
    }

    @Override
    public boolean editGift(Long id, Gift gift) {
        if (!giftRepository.exists(id)) {
            return false;
        }

        Gift oldGift = giftRepository.findOne(id);
        gift.setId(oldGift.getId());

        giftRepository.save(gift);

        return true;
    }
}
