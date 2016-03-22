package pl.edu.agh.io.wishlist.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.service.FriendService;

import java.util.List;

@RestController
public class FriendController {
    @Autowired
    @Qualifier("friendService")
    private FriendService friendService;

    @RequestMapping("/getFreinds/{id}")
    public List<User> getFriends(@PathVariable(value = "id") Long id) {
        return friendService.getFriends(id);
    }

    @RequestMapping("/addfriend/{friendId}")
    public boolean addFriend(@PathVariable(value="friendId") Long friendId, @RequestParam(value="userId") Long userId) {
        return friendService.addFriend(userId, friendId);
    }

    @RequestMapping("/deletefriend/{friendId}")
    public boolean deleteFriend(@PathVariable(value="friendId") Long friendId, @RequestParam(value="userId") Long userId) {
        return friendService.deleteFriend(userId, friendId);
    }
}
