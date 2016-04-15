package pl.edu.agh.io.wishlist.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.service.IGiftService;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

// TODO: 12/04/2016
// refactor it with a REST-friendly way

@RestController
@RequestMapping("/gifts")
public class GiftListController {

    @Autowired
    private IGiftService giftService;

    private final AtomicLong counter = new AtomicLong();

    @ResponseBody
    @RequestMapping(value = "/add/{userId}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> addGift(@PathVariable String userId, @RequestBody Gift gift) {
        System.out.println("Id: " + gift.getId() + "\nName: " + gift.getName() + "\nDesc: " + gift.getDescription());
        if (giftService.addGift(userId, gift))
            return new ResponseEntity<>("Gift added", HttpStatus.OK);
        return new ResponseEntity<>("Cannot add gift", HttpStatus.CONFLICT);
    }

    @ResponseBody
    @RequestMapping(value = "/forUser/{userId}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Gift>> getAllGifts(@PathVariable String userId) {
        return new ResponseEntity<>(giftService.getAllGifts(userId), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/getGift/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Gift> getGift(@PathVariable String id) {
        Gift gift = giftService.getGift(id);
        if (gift == null)
            return new ResponseEntity<>((Gift) null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(gift, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/remove/{userId}/{giftId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeGift(@PathVariable String userId, @PathVariable String giftId) {
        if (giftService.removeGift(userId, giftId))
            return new ResponseEntity<>("Gift removed", HttpStatus.OK);
        return new ResponseEntity<>("Cannot remove gift", HttpStatus.CONFLICT);
    }

    @ResponseBody
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<String> updateGift(@PathVariable String id, @RequestBody Gift gift) {
        if (giftService.editGift(id, gift))
            return new ResponseEntity<>("Gift updated", HttpStatus.OK);
        return new ResponseEntity<>("Cannot update gift", HttpStatus.CONFLICT);
    }

}
