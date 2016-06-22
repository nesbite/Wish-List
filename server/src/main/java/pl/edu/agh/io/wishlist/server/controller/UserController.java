package pl.edu.agh.io.wishlist.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.UserRepository;
import pl.edu.agh.io.wishlist.service.IUserService;

import java.security.Principal;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<User> getUser(Principal principal) {
        return new ResponseEntity<>(userService.getUserByUsername(principal.getName()), HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<String> updateUser(Principal principal, @RequestBody User user) {
        if(principal.getName().equals(user.getUsername())){
            userService.update(user);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
