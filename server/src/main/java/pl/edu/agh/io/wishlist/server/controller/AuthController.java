package pl.edu.agh.io.wishlist.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.io.wishlist.service.IAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService authService;

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void register(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        authService.register(username, password);
    }

    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public void login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
        authService.login(username, password);
    }


}
