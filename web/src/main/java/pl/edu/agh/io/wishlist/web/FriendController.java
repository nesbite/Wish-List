package pl.edu.agh.io.wishlist.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.service.FriendService;

import java.util.List;

@RestController
@RequestMapping("/friends")
public class FriendController {
    @Autowired
    @Qualifier("friendService")
    private FriendService friendService;

    @ResponseBody
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = "application/json")
    public List<User> getFriends(@PathVariable(value = "id") Long id) {
        return friendService.getFriends(id);
    }

    @ResponseBody
    @RequestMapping(value = "/add/{userId}", method = RequestMethod.PUT)
    public boolean addFriend(@PathVariable(value = "userId") Long userId, @RequestParam(value = "friendId") Long friendId) {
        return friendService.addFriend(userId, friendId);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{userId}", method = RequestMethod.DELETE)
    public boolean deleteFriend(@PathVariable(value = "userId") Long userId, @RequestParam(value = "friendId") Long friendId) {
        return friendService.deleteFriend(userId, friendId);
    }
}
