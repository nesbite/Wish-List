package pl.edu.agh.io.wishlist.persistence.dao.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import pl.edu.agh.io.wishlist.domain.User;
import pl.edu.agh.io.wishlist.persistence.dao.FriendDAO;

import java.util.List;
@Component
public class FriendMongoDAO implements FriendDAO {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<User> getAll(Long id) {
        Query userQuery = new Query();
        userQuery.addCriteria(Criteria.where("_id").is(id));
        User user = mongoTemplate.findOne(userQuery, User.class);
        if(user == null){
            return null;
        }
        List<Long> friendsIdList = user.getFriends();

        Query friendsQuery = new Query();
        friendsQuery.addCriteria(Criteria.where("_id").in(friendsIdList));
        return mongoTemplate.find(friendsQuery, User.class);
    }

    @Override
    public boolean add(Long userId, Long friendId) {
        Query userQuery = new Query();
        userQuery.addCriteria(Criteria.where("_id").is(userId));
        User user = mongoTemplate.findOne(userQuery, User.class);

        Query friendQuery = new Query();
        friendQuery.addCriteria(Criteria.where("_id").is(friendId));
        User friend = mongoTemplate.findOne(friendQuery, User.class);
        if(user == null || friend == null){
            return false;
        }
        List<Long> friendsIdList = user.getFriends();
        if(friendsIdList.contains(friendId)){
            return false;
        }
        mongoTemplate.findAndModify(userQuery, new Update().addToSet("friends", friendId), User.class);
        return true;
    }

    @Override
    public boolean delete(Long userId, Long friendId) {
        Query userQuery = new Query();
        userQuery.addCriteria(Criteria.where("_id").is(userId));
        User user = mongoTemplate.findOne(userQuery, User.class);
        if(user == null){
            return false;
        }
        List<Long> friendsIdList = user.getFriends();
        Query removeQuery = Query.query(Criteria.where("_id").is(userId).and("friends.id").in(friendsIdList));
        if(friendsIdList.contains(friendId)){
            mongoTemplate.upsert(removeQuery, new Update().pull("friends", friendId), User.class);
            return true;
        }
        return false;
    }
}
