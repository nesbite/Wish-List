package pl.edu.agh.io.wishlist.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json")
    public boolean addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/get/{login}", method = RequestMethod.GET, produces = "application/json")
    public User getUser(@PathVariable(value = "login") String login) {
        return userService.getUser(login);
    }

}
