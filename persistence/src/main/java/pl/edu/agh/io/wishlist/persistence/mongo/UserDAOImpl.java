package pl.edu.agh.io.wishlist.persistence.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.UserDAO;

import java.net.UnknownServiceException;
import java.nio.file.attribute.UserDefinedFileAttributeView;
@Component
public class UserDAOImpl implements UserDAO {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public boolean addUser(User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("login").is(user.getLogin()));
        if(mongoTemplate.findOne(query, User.class) != null){
            return false;
        }
        long id =(long)Double.parseDouble(getNextSequence("userID", mongoTemplate).toString());
        user.setId(id);
        mongoTemplate.insert(user);
        return true;
    }

    @Override
    public User getUser(String login) {
        Query query = new Query();
        query.addCriteria(Criteria.where("login").is(login));
        return mongoTemplate.findOne(query, User.class);
    }

    private static Object getNextSequence(String name, MongoTemplate mongoTemplate) {
        BasicDBObject find = new BasicDBObject();
        find.put("_id", name);
        BasicDBObject update = new BasicDBObject();
        update.put("$inc", new BasicDBObject("seq", 1));
        DBObject obj = mongoTemplate.getCollection("counters").findAndModify(find, update);
        return obj.get("seq");
    }
}
