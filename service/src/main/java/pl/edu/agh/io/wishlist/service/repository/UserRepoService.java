package pl.edu.agh.io.wishlist.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.repository.mongo.UserRepository;
import pl.edu.agh.io.wishlist.service.IUserService;

@Service("userRepoService")
public class UserRepoService implements IUserService {

    @Autowired
    UserRepository repository;

    @Override
    public boolean addUser(User user) {
        if (repository.exists(user.getId())) {
            return false;
        }

        repository.save(user);
        return true;
    }

    @Override
    public User getUser(String login) {
        return repository.findByLogin(login);
    }
}
