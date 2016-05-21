package pl.edu.agh.io.wishlist.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.persistence.UserRepository;
import pl.edu.agh.io.wishlist.service.IGiftService;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

// TODO: 12/04/2016
// refactor it with a REST-friendly way

@CrossOrigin
@RestController
@RequestMapping("/gifts")
public class GiftListController {

    @Autowired
    private IGiftService giftService;

    @Autowired
    UserRepository userRepository;


    private final AtomicLong counter = new AtomicLong();

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<Gift>> getAllGifts(Principal principal) {
        String userId = userRepository.findByUsername(principal.getName()).getId();
        return new ResponseEntity<>(giftService.getAllGifts(userId), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> addGift(Principal principal, @RequestBody Gift gift) {
        System.out.println("Id: " + gift.getId() + "\nName: " + gift.getName() + "\nDesc: " + gift.getDescription());
        String userId = userRepository.findByUsername(principal.getName()).getId();
        if (giftService.addGift(userId, gift))
            return new ResponseEntity<>("Gift added", HttpStatus.OK);
        return new ResponseEntity<>("Cannot add gift", HttpStatus.CONFLICT);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Gift> getGift(@PathVariable String id) {
        Gift gift = giftService.getGift(id);
        if (gift == null)
            return new ResponseEntity<>((Gift) null, HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(gift, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/remove/{giftId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> removeGift(Principal principal, @PathVariable String giftId) {
        String userId = userRepository.findByUsername(principal.getName()).getId();
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
