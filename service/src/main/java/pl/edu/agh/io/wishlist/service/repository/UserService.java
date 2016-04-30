package pl.edu.agh.io.wishlist.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.UserRepository;
import pl.edu.agh.io.wishlist.persistence.sequence.SequenceRepository;
import pl.edu.agh.io.wishlist.service.IUserService;
import pl.edu.agh.io.wishlist.service.exceptions.UserAlreadyExistsException;
import pl.edu.agh.io.wishlist.service.exceptions.UserNotFoundException;

import java.util.Collection;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository repository;

    @Autowired
    SequenceRepository sequenceRepository;

    @Override
    public void addUser(User user) {
        if (repository.findByUsername(user.getUsername()) != null) {
            throw new UserAlreadyExistsException(user.getUsername());
        }

        repository.save(user);
    }

    @Override
    public User getUser(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public Collection<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public void update(User user) {
        if (repository.findByUsername(user.getUsername()) == null) {
            throw new UserNotFoundException(user.getUsername());
        }

        repository.save(user);
    }

}
