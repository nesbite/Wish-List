package pl.edu.agh.io.wishlist.persistence.dao.mongo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import pl.edu.agh.io.wishlist.domain.Gift;
import pl.edu.agh.io.wishlist.persistence.dao.GiftDAO;

import java.util.List;

@Component
public class GiftMongoDAO implements GiftDAO{

    @Autowired
    MongoTemplate mongoTemplate;


    @Override
    public Gift get(Long giftID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(giftID));
        return mongoTemplate.findOne(query, Gift.class);
    }

    @Override
    public List<Gift> getAll(Long userID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("userID").is(userID));
        return mongoTemplate.find(query, Gift.class);
    }

    @Override
    public boolean put(Gift gift) {
//        long id = (long) Double.parseDouble(getNextSequence("giftID", mongoTemplate).toString());
//        gift.setId(id);
        mongoTemplate.insert(gift);
        return true;
    }

    @Override
    public boolean remove(Long giftID) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(giftID));
        mongoTemplate.findAndRemove(query, Gift.class);
        return true;
    }

    @Override
    public boolean update(Long giftID, Gift gift) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(giftID));
        mongoTemplate.updateFirst(query, new Update().set("name", gift.getName()).set("description", gift.getDescription()), Gift.class);
        return true;
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
