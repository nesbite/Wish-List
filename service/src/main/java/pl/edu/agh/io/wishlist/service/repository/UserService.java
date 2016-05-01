package pl.edu.agh.io.wishlist.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.PasswordResetToken;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.domain.UserDto;
import pl.edu.agh.io.wishlist.domain.VerificationToken;
import pl.edu.agh.io.wishlist.domain.validation.EmailExistsException;
import pl.edu.agh.io.wishlist.persistence.PasswordResetTokenRepository;
import pl.edu.agh.io.wishlist.persistence.RoleRepository;
import pl.edu.agh.io.wishlist.persistence.UserRepository;
import pl.edu.agh.io.wishlist.persistence.VerificationTokenRepository;
import pl.edu.agh.io.wishlist.service.IUserService;
import pl.edu.agh.io.wishlist.service.exceptions.UserNotFoundException;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationTokenRepository tokenRepository;

    @Autowired
    private PasswordResetTokenRepository passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Collection<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void update(User user) {
        if (userRepository.findByEmail(user.getEmail()) == null) {
            throw new UserNotFoundException(user.getEmail());
        }
        userRepository.save(user);
    }

    // API

    @Override
    public User registerNewUserAccount(final UserDto accountDto) throws EmailExistsException {
        if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException("There is an account with that email adress: " + accountDto.getEmail());
        }
        final User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());

        user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        return userRepository.save(user);
    }

    @Override
    public User getUser(final String verificationToken) {
        final User user = tokenRepository.findByToken(verificationToken).getUser();
        return user;
    }

    @Override
    public VerificationToken getVerificationToken(final String VerificationToken) {
        return tokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void saveRegisteredUser(final User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(final User user) {
        userRepository.delete(user);
    }

    @Override
    public void createVerificationTokenForUser(final User user, final String token) {
        final VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken generateNewVerificationToken(final String existingVerificationToken) {
        VerificationToken vToken = tokenRepository.findByToken(existingVerificationToken);
        vToken.updateToken(UUID.randomUUID().toString());
        vToken = tokenRepository.save(vToken);
        return vToken;
    }

    @Override
    public void createPasswordResetTokenForUser(final User user, final String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordTokenRepository.save(myToken);
    }

    @Override
    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public PasswordResetToken getPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token);
    }

    @Override
    public User getUserByPasswordResetToken(final String token) {
        return passwordTokenRepository.findByToken(token).getUser();
    }

    @Override
    public User getUserByID(final String id) {
        return userRepository.findOne(id);
    }

    @Override
    public void changeUserPassword(final User user, final String password) {
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public boolean checkIfValidOldPassword(final User user, final String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    private boolean emailExist(final String email) {
        final User user = userRepository.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }

}
