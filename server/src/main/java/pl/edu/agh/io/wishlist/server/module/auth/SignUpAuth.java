package pl.edu.agh.io.wishlist.server.module.auth;

import pl.edu.agh.io.wishlist.domain.module.User;

public interface SignUpAuth {
    User signUp(String username, String password);

}
