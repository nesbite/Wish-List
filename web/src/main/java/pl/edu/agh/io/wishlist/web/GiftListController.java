package pl.edu.agh.io.wishlist.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.service.GiftService;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/gifts")
public class GiftListController {

    @Autowired
    @Qualifier("giftService")
    private GiftService giftService;

    private final AtomicLong counter = new AtomicLong();
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> addGift(@RequestBody Gift gift) {
        System.out.println("Id: "+ gift.getId()+"\nName: " + gift.getName() + "\nDesc: "+gift.getDescription());
        if(giftService.addGift(gift.getId(), gift))
            return new ResponseEntity<>("Gift added", HttpStatus.OK);
        return new ResponseEntity<>("Cannot add gift", HttpStatus.CONFLICT);
    }
    @ResponseBody
    @RequestMapping(value="/forUser/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Gift>> getAllGifts(@PathVariable Long id) {
        return new ResponseEntity<>(giftService.getAllGifts(id), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/getGift/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Gift> getGift(@PathVariable Long id) {
        Gift gift = giftService.getGift(id);
        if(gift == null)
            return new ResponseEntity<>((Gift) null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(gift, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> removeGift(@PathVariable Long id) {
        if(giftService.removeGift(id))
            return new ResponseEntity<>("Gift removed", HttpStatus.OK);
        return new ResponseEntity<>("Cannot remove gift", HttpStatus.CONFLICT);
    }

    @ResponseBody
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> updateGift(@PathVariable Long id, @RequestBody Gift gift) {
        if(giftService.editGift(id, gift.getName(), gift.getDescription()))
            return new ResponseEntity<>("Gift updated", HttpStatus.OK);
        return new ResponseEntity<>("Cannot update gift", HttpStatus.CONFLICT);
    }

}
