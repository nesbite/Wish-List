package pl.edu.agh.io.wishlist.server.module.auth;

import pl.edu.agh.io.wishlist.domain.module.User;

public interface Authenticator {

    Session authenticate(User user);

    Session getSession(User user);
}
