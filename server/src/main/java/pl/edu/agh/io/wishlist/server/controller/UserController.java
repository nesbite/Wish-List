package pl.edu.agh.io.wishlist.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.domain.UserDetails;
import pl.edu.agh.io.wishlist.service.IUserService;
import pl.edu.agh.io.wishlist.service.exceptions.UserNotFoundException;
import pl.edu.agh.io.wishlist.service.repository.UserDetailsService;

import java.util.Collection;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private UserDetailsService userDetailsService;

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

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }


    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json")
    // TODO do not response with boolean
    public boolean addUser(@RequestBody UserDetails user) {
        return userDetailsService.registerUser(user);
    }


}
