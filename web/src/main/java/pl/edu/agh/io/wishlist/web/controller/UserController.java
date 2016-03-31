package pl.edu.agh.io.wishlist.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.domain.UserDetails;
import pl.edu.agh.io.wishlist.service.IUserService;
import pl.edu.agh.io.wishlist.service.repository.UserDetailsService;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json")
    public boolean addUser(@RequestBody UserDetails user) {
        return userDetailsService.registerUser(user);
    }

    @ResponseBody
    @RequestMapping(value = "/{login}", method = RequestMethod.GET, produces = "application/json")
    public User getUser(@PathVariable(value = "login") String login) {
        return userService.getUser(login);
    }

}
