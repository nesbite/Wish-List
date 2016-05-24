package pl.edu.agh.io.wishlist.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.UserRepository;
import pl.edu.agh.io.wishlist.service.IFriendService;

import java.security.Principal;
import java.util.List;

// TODO: 12/04/2016
// refactor it with a REST-friendly way

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    private IFriendService friendService;

    @Autowired
    UserRepository userRepository;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<User>> getFriends(Principal principal) {
        System.out.println(principal.getName());
        return  new ResponseEntity<>(friendService.getFriends(principal.getName()), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/add/{friendId}", method = RequestMethod.PUT)
    public ResponseEntity<String> addFriend(Principal principal, @PathVariable(value = "friendId") String friendId) {
        String userId = userRepository.findByUsername(principal.getName()).getId();
        if(friendService.addFriend(userId, friendId)){
            return new ResponseEntity<>("Friend added", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cannot add friend", HttpStatus.CONFLICT);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{friendId}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFriend(Principal principal, @PathVariable(value = "friendId") String friendId) {
        String userId = userRepository.findByUsername(principal.getName()).getId();
        if(friendService.deleteFriend(userId, friendId)){
            return new ResponseEntity<>("Friend deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Cannot delete friend", HttpStatus.CONFLICT);
    }
}