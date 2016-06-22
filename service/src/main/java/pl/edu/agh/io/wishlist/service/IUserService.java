package pl.edu.agh.io.wishlist.service;

import pl.edu.agh.io.wishlist.domain.PasswordResetToken;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.domain.VerificationToken;
import pl.edu.agh.io.wishlist.domain.validation.EmailExistsException;
import pl.edu.agh.io.wishlist.service.exceptions.UserNotFoundException;
import pl.edu.agh.io.wishlist.domain.UserDto;

import java.util.Collection;

public interface IUserService {
    Collection<User> getUsers();
    void update(User user) throws UserNotFoundException;
    User registerNewUserAccount(UserDto accountDto) throws EmailExistsException;
    User getUser(String verificationToken);
    User getUserByEmail(String email);
    User getUserByUsername(String username);
    void saveRegisteredUser(User user);
    void deleteUser(User user);
    void createVerificationTokenForUser(User user, String token);
    VerificationToken getVerificationToken(String VerificationToken);
    VerificationToken generateNewVerificationToken(String token);
    void createPasswordResetTokenForUser(User user, String token);
    User findUserByEmail(String email);
    PasswordResetToken getPasswordResetToken(String token);
    User getUserByPasswordResetToken(String token);
    User getUserByID(String id);
    void changeUserPassword(User user, String password);
    boolean checkIfValidOldPassword(User user, String password);
}
