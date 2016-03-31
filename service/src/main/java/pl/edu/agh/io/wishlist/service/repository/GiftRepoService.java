package pl.edu.agh.io.wishlist.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.GiftRepository;
import pl.edu.agh.io.wishlist.persistence.UserRepository;
import pl.edu.agh.io.wishlist.service.IGiftService;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiftRepoService implements IGiftService {

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
        List<String> giftList = userRepository.findOne(userId).getGifts();
        List<Gift> gifts = new ArrayList<>();
        giftRepository.findAll(giftList).forEach(gifts::add);
        return gifts;
    }

    @Override
    public boolean addGift(String userId, Gift gift) {

        if (!userRepository.exists(userId)) {
            return false;
        }
        User user = userRepository.findOne(userId);
        giftRepository.save(gift);
        user.getGifts().add(gift.getId());
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean removeGift(String userId, String giftId) {
        if (!giftRepository.exists(giftId) || !userRepository.exists(userId)) {
            return false;
        }
        User user = userRepository.findOne(userId);
        List<String> giftIdList = user.getGifts();
        if(!giftIdList.contains(giftId)){
            return false;
        }
        giftIdList.remove(giftId);
        userRepository.save(user);
        giftRepository.delete(giftId);

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
