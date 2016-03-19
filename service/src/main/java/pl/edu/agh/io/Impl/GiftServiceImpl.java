package pl.edu.agh.io.Impl;

import org.springframework.stereotype.Service;
import pl.edu.agh.io.Gift;
import pl.edu.agh.io.GiftService;

import java.util.List;

@Service
public class GiftServiceImpl implements GiftService {
    public boolean addGift(Gift gift) {
        return false;
    }

    public Gift getGift(Long id) {
        return null;
    }

    public List<Gift> getAllGifts() {
        return null;
    }

    public boolean removeGift(Long id) {
        return false;
    }

    public boolean updateGift(Long id, String name, String description) {
        return false;
    }
}
