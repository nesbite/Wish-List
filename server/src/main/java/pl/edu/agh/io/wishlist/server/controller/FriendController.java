package pl.edu.agh.io.wishlist.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.service.IFriendService;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    private IFriendService friendService;

    @ResponseBody
    @RequestMapping(value = "/getAll/{id}", method = RequestMethod.GET, produces = "application/json")
    public List<User> getFriends(@PathVariable(value = "id") String id) {
        return friendService.getFriends(id);
    }

    @ResponseBody
    @RequestMapping(value = "/add/{userId}", method = RequestMethod.PUT)
    public boolean addFriend(@PathVariable(value = "userId") String userId, @RequestParam(value = "friendId") String friendId) {
        return friendService.addFriend(userId, friendId);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
    public boolean deleteFriend(@PathVariable(value = "userId") String userId, @RequestParam(value = "friendId") String friendId) {
        return friendService.deleteFriend(userId, friendId);
    }
}