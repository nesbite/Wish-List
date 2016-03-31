package pl.edu.agh.io.wishlist.service;

import pl.edu.agh.io.wishlist.domain.UserDetails;

public interface IUserDetailsService {
    boolean registerUser(UserDetails userDetails);
    boolean changePassword(String userId, String password);
    boolean changeEmail(String userId, String email);
}
