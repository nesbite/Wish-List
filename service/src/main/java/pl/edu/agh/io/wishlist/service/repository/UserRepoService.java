package pl.edu.agh.io.wishlist.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.UserRepository;
import pl.edu.agh.io.wishlist.persistence.sequence.SequenceRepository;
import pl.edu.agh.io.wishlist.service.IUserService;

@Service
public class UserRepoService implements IUserService {

    @Autowired
    UserRepository repository;

    @Autowired
    SequenceRepository sequenceRepository;

    @Override
    public boolean addUser(User user) {
        if (repository.findByUsername(user.getUsername()) != null) {
            return false;
        }
//        user.setId(sequenceRepository.getNextSequenceId("userID"));
        repository.save(user);
        return true;
    }

    @Override
    public User getUser(String login) {
        return repository.findByUsername(login);
    }

}
