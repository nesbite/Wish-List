package pl.edu.agh.io.wishlist.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.service.IUserService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public Collection<User> getUsers() {
        return userService.getUsers();
    }

    @ResponseBody
    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public User getUser(@PathVariable(value = "username") String username) {
        return userService.getUser(username);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.PUT)
    public void updateUser(@RequestBody User user) {
        userService.update(user);
    }

}
