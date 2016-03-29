package pl.edu.agh.io.wishlist.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.repository.mongo.SequenceRepository;
import pl.edu.agh.io.wishlist.persistence.repository.mongo.GiftRepository;
import pl.edu.agh.io.wishlist.persistence.repository.mongo.UserRepository;
import pl.edu.agh.io.wishlist.service.IGiftService;

import java.util.ArrayList;
import java.util.List;


public class GiftRepoService implements IGiftService {

    @Autowired
    GiftRepository giftRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SequenceRepository sequenceRepository;


    @Override
    public Gift getGift(Long id) {
        return giftRepository.findOne(id);
    }

    @Override
    public List<Gift> getAllGifts(Long userId) {
        List<Long> giftList = userRepository.findOne(userId).getGifts();
        List<Gift> gifts = new ArrayList<>();
        giftRepository.findAll(giftList).forEach(gifts::add);
        return gifts;
    }

    @Override
    public boolean addGift(Long userId, Gift gift) {

        if (!userRepository.exists(userId)) {
            return false;
        }
        gift.setId(sequenceRepository.getNextSequenceId("giftID"));
        User user = userRepository.findOne(userId);
        giftRepository.save(gift);
        user.getGifts().add(gift.getId());
        userRepository.save(user);

        return true;
    }

    @Override
    public boolean removeGift(Long userId, Long giftId) {
        if (!giftRepository.exists(giftId) || !userRepository.exists(userId)) {
            return false;
        }
        User user = userRepository.findOne(userId);
        List<Long> giftIdList = user.getGifts();
        if(!giftIdList.contains(giftId)){
            return false;
        }
        giftIdList.remove(giftId);
        userRepository.save(user);
        giftRepository.delete(giftId);

        return true;
    }

    @Override
    public boolean editGift(Long id, Gift gift) {
        if (!giftRepository.exists(id)) {
            return false;
        }

        Gift oldGift = giftRepository.findOne(id);
        gift.setId(id);

        giftRepository.save(gift);

        return true;
    }
}
