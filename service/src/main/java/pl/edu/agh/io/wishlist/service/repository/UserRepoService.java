package pl.edu.agh.io.wishlist.service.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.dao.SequenceDAO;
import pl.edu.agh.io.wishlist.persistence.repository.mongo.UserRepository;
import pl.edu.agh.io.wishlist.service.IUserService;

@Service("userRepoService")
public class UserRepoService implements IUserService {

    @Autowired
    UserRepository repository;

    @Autowired
    SequenceDAO sequenceDAO;

    @Override
    public boolean addUser(User user) {
        if (repository.findByLogin(user.getLogin()) != null) {
            return false;
        }
        user.setId(sequenceDAO.getNextSequenceId("userID"));
        repository.save(user);
        return true;
    }

    @Override
    public User getUser(String login) {
        return repository.findByLogin(login);
    }

}
