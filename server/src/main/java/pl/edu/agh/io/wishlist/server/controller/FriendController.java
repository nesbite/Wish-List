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
    @RequestMapping(value = "/add/{friendName}", method = RequestMethod.PUT)
    public ResponseEntity<String> addFriend(Principal principal, @PathVariable(value = "friendName") String friendName) {
        if(!principal.getName().equalsIgnoreCase(friendName)) {
            User user = userRepository.findByUsername(principal.getName());
            if (user != null) {
                String username = user.getUsername();
                if (friendService.addFriend(username, friendName)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{friendName}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFriend(Principal principal, @PathVariable(value = "friendName") String friendName) {
        User user =  userRepository.findByUsername(principal.getName());
        if(user !=null) {
            String username = user.getUsername();
            if (friendService.deleteFriend(username, friendName)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ResponseBody
    @RequestMapping(value = "/requests", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<List<User>> getFriendsRequests(Principal principal) {
        System.out.println(principal.getName());
        return  new ResponseEntity<>(friendService.getFriendsRequests(principal.getName()), HttpStatus.OK);
    }
    @ResponseBody
    @RequestMapping(value = "/requests/reject/{friendName}", method = RequestMethod.PUT)
    public ResponseEntity<String> rejectFriendRequest(Principal principal, @PathVariable(value = "friendName") String friendName) {
        if(!principal.getName().equalsIgnoreCase(friendName)) {
            User user = userRepository.findByUsername(principal.getName());
            if (user != null) {
                String username = user.getUsername();
                if (friendService.rejectRequest(username, friendName)) {
                    return new ResponseEntity<>(HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ResponseBody
    @RequestMapping(value = "/publish/{friendName}", method = RequestMethod.PUT)
    public ResponseEntity<String> publishListToFriend(Principal principal, @PathVariable(value = "friendName") String friendName) {
        User user =  userRepository.findByUsername(principal.getName());
        if(user != null) {
            String username = user.getUsername();
            if (friendService.addUserToFriendsFriendList(username, friendName)) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

}