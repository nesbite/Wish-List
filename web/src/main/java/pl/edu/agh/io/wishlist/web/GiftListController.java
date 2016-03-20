package pl.edu.agh.io.wishlist.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.service.GiftService;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GiftListController {

    @Autowired
    @Qualifier("giftService")
    private GiftService giftService;

    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/addgift/{id}")
    public boolean addGift(@PathVariable(value="id") Long id, @RequestParam(value="name") String name, @RequestParam(value="desc", defaultValue="") String description) {
        return giftService.addGift(id, new Gift(id, name, description));
    }

    @RequestMapping("/getallgifts/{id}")
    public List<Gift> getAllGifts(@PathVariable(value="id") Long id) {
        return giftService.getAllGifts(id);
    }

    @RequestMapping("/getgift/{id}")
    public Gift getGift(@PathVariable(value="id") Long id) {
        return giftService.getGift(id);
    }

    @RequestMapping("/removegift/{id}")
    public boolean removeGift(@PathVariable(value="id") Long id) {
        return giftService.removeGift(id);
    }

    @RequestMapping("/updategift/{id}")
    public boolean updateGift(@PathVariable(value="id") Long id, @RequestParam(value="newname") String newName, @RequestParam(value="desc") String description) {
        return giftService.editGift(id, newName, description);
    }

}
