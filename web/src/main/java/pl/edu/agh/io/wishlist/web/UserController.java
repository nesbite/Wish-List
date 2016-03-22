package pl.edu.agh.io.wishlist.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.service.UserService;

@RestController
public class UserController {
    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @RequestMapping("/adduser/{id}")
    public boolean addUser(@PathVariable(value="id") Long id, @RequestParam(value="login") String login, @RequestParam(value="password") String password) {
        return userService.addUser(new User(id, login, password));
    }

    @RequestMapping("/getuser/{login}")
    public User getUser(@PathVariable(value="login") String login) {
        return userService.getUser(login);
    }

}
