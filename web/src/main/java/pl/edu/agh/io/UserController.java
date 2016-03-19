package pl.edu.agh.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/getUser/{username}")
    public User getUser(@PathVariable("username") String username){
        User user = userService.getUser(username);
//        if (user == null) return new ResponseEntity<>((User) null, HttpStatus.NOT_FOUND);
//        return new ResponseEntity<>(user, HttpStatus.OK);
//        return user;
        return user;
    }


}
