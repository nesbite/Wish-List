package pl.edu.agh.io.wishlist.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.service.IUserService;
import pl.edu.agh.io.wishlist.service.exceptions.UserAlreadyExistsException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IUserService userService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public void login() {
        /**
         * Spring Security
         * @see pl.edu.agh.io.wishlist.server.WebSecurityConfiguration
         */
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public User register(@PathVariable(value = "username") String username, @PathVariable(value = "password") String password) {
        if (userService.getUser(username) != null) {
            throw new UserAlreadyExistsException(username);
        }

        User user = new User(username, password);
        userService.addUser(user);

        return user;
    }

}
